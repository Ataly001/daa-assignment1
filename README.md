A) Project overview:
In this project I implemented 4 "divide and conquer" algorithms
MergeSorter, QuickSorter, Deterministic Selector and Closest Pair algorithm.
The goal was to see the theoretical complexity and compare it 
with the results that we get.

B) Analysis of Algorithms

1: MergeSort- it divides the array in half and sorts the halves
recursively
Complexity: O(n Logn)
Memory: O(n)
Master Theorem: T(n)=2T(n/2)+O(n)
Case 2: Theta(n log n)

2: QuickSort
Chooses a pivot, Divide to smaller than pivot and greater, and sorts them recursively
Complexity: Average O(n log n), worst-case O(n^2)
Memory: O(log n)
Recurrence: T(n) = T(k) + T(n-k-1) + O(n)

3: Deterministic Select (Median of Medians)
It divides array into groups of 5, finds their medians, 
and finds the median of that medians to use as a good pivot.
Complexity: O(n)
Memory: O(log n)
Recurrence: T(n) <= T(n/5) + T(7n/10) + O(n)
Akra-Bazzi intuition: 1/5 + 7/10 = 9/10 < 1, which gives O(n).


4: Closest Pair of Points
Sorts points by X, divides them in halves, finds minimum distance
and checks the middle strip by Y coordinate.
Complexity: O(n log n)
Memory: O(n)
Master Theorem: T(n) = 2T(n/2) + O(n)
Case 2: Theta(n log n)


C) Experimental Results:
Results are saved in results/results.csv.
Plots are in docs/plots.

D) Discussion
1)Do results match theory?
Yes, the numbers stabilize as size grows from 10k to 100k.
For example, QuickSort Random ratio stays about 5.8 - 6.1, which gives O(n log n) complexity.

2)How does input structure affect performance?
MergeSort works equally on all types. QuickSort is fast on random and sorted arrays,
but on Duplicate arrays at size 100,000 the time growth. This shows the O(n^2) worst case on duplicates.

3)Why smaller-first recursion in QuickSort?
It guarantees that recursion goes into the smaller part first, keeping the call stack in O(log n). 
In my results - MaxDepth never exceeded 11 even in worst case.


4)Why Median-of-Medians guarantees O(n)?
Because the pivot is always chosen close to the real median. It guarantees to clear
at least 30% of elements every step, avoiding bad partitions.


5)Why Closest Pair is faster than O(n^2)?
It doesn't compare all pairs, it divides points and only 
checks 7-8 points in every point in the middle strip and it 
gives O(n) time for the merge step.


6)Practical factors affecting performance:
I didn't see any affections because of JVM, but I noticed 
big memory allocations in some tests: 
auxiliary arrays in MergeSort and 
duplicating large arrays (100,000 elements)


E) Reflection:

I learned how divide and conquer algorithms work in practice
learned how to find out complexities of algorithms using Master Theorem and
Master Theorems special cases. Learned that complexities in theory and in practice
may have some differences because of some conditions, for example, in quick sort
when applying duplicate elements complexity grown in O(n)^2 which was worst case 
and I realised if the input numbers were bigger the amount of operations
would be too huge (array of million elements would take trillion operations to deal with).
In conclusion, it was very useful experience that gave me understanding of 
Divide and Conquer algorithms.

F) Screenshots:
Screenshots of tests and console output are saved in docs/screenshots/.
