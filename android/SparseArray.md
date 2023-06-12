####  SparseArray

稀疏数组

在某些条件下性能更好，主要是因为它避免了对key的自动装箱（int转为Integer类型），它内部则是通过两个数组来进行数据存储的，一个存储key，另外一个存储value，为了优化性能，它内部对数据还采取了压缩的方式来表示稀疏数组的数据，从而节约内存空间。



查找依赖于二分查找

添加、查找、删除数据都需要先进行一次二分查找，在**数据量大**的情况下性能并不明显，将降低至少50%。



**满足下面两个条件我们可以使用SparseArray代替HashMap：**

- 数据量不大，最好在千级以内，如果在数据量比较大的情况下，那么它的性能将退化至少50%。
- key必须为int类型，这种情况下的HashMap可以用SparseArray代替。


