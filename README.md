# Java_git_system
## 项目简介
Java 程序设计课程项目：《实现一个简单的版本管理工具》（参考 git 实现原理）

## 要实现的功能点
- 可以提交 `commit`，可以进行 `git log` 查看 commit 历史
- 可以进行 `git reset` 回滚到指定 commit
- 可创建多分支，可在分支之间切换

## 项目进度
### 第一周
#### 任务一：实现 key-value 存储
最简单的 key-value 存储方式 (filename -> content of file)
- Key 作为文件名，文件内容作为 value
- 给定 value，以下以 "hello world" 为例
```
Hash ("hello world") == 34234234
创建文件 objects / 34234234 --> hello world
给定 34234234，要找到 value 的值
```

支持以下功能
- 给定 value，向存储中添加对应的 key-value
- 给定 key，查找得到对应的 value 值

#### 任务二：将一个文件夹转化成 key, value
给定一个文件夹目录，将其转化成若干 tree 和 blob
- 深度优先遍历此目录:
  - 遇到子文件就转化成 blob 并保存
  - 遇到子文件夹就递归调用其内部的子文件/文件夹最后构造 tree 并保存

使用任务一提供的接口 --- hash 表

### 第二周
#### 完善、优化已有代码
实现 `Commit`
- 给定一个工作区目录，生成对应的 blob 和 tree（上周已完成）以及 commit
- 写代码之前先理清思路，写设计文档
- 提示：
  - 需要存储指向当前最新 commit 的 HEAD 指针
  - 每次新生成一个 commit 前，需要把根目录的 tree key 与已有的最新 commit 的 tree key 进行比较，发现不相同时（即文件发生了变动）才添加这个 commit
