XOR 是 exclusive OR 的缩写

exclusive 意思是专有的，独有的，可以理解为更单纯的 OR 运算



XOR 运算是两个值不通为true，相同为 false

如果约定 0 为false，1为 true

```
0 ^ 0 = 0
0 ^ 1 = 1
1 ^ 1 = 0
```



运算定律

1.一个值与自身运算，值为 false

x ^ x = 0

2.一个值与 0 运算，值为本身

x ^ 0 = x

3.可交换性

x ^ y = y ^ x

4.结合性

x ^ (y ^ z) = (x ^ y) ^ z



两个变量连续进行三次异或运算，可以相互交换值

x = x ^ y  (x = a, y = b)

y = x ^ y  (y = a)

x = x ^ y  (x = b)





Java中的位运算：https://blog.csdn.net/f641385712/article/details/103824846















