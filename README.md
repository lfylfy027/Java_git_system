# Java_git_system
###项目简介
Java程序设计课程项目：《实现一个简单的版本管理工具》（参考git实现原理）

###要实现的功能点
可以提交commit，可以进行”git log”查看commit历史
可以进行”git reset”回滚到指定commit
可创建多分支，可在分支之间切换

###项目进度
##第一周
#任务一：实现key-value存储

最简单的key-value存储方式（filename -> content of file）
  1.Key作为文件名，文件内容作为value
  2.给定value：“hello world”
  3.Hash(“hello world”) == 34234234
  4.创建文件 objects/34234234 --> hello world
  5.给定34234234，要找到value的值

支持以下功能
  1.给定value，向存储中添加对应的key-value
  2.给定key，查找得到对应的value值


#任务二：将一个文件夹转化成key,value

给定一个文件夹目录，将其转化成若干tree和blob
  1.深度优先遍历此目录:
    a.遇到子文件就转化成blob并保存
    b.遇到子文件夹就递归调用其内部的子文件/文件夹最后构造tree并保存

使用任务1提供的接口 ---hash表

##第二周

完善、优化已有代码

实现Commit

  1.给定一个工作区目录，生成对应的blob和tree(上周已完成)以及commit
  2.写代码之前先理清思路，写设计文档
  3.提示：
    a.需要存储指向当前最新commit的HEAD指针
    b.每次新生成一个commit前，需要把根目录的tree key与已有的最新commit的tree key进行比较，发现不相同时（即文件发生了变动）才添加这个commit。
