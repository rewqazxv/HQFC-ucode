# 微指令编译器

适用于 HQFC-BY 计算机组成原理仿真系统和 TEC-9 计算机组成原理实验系统的微指令翻译器和编译器，实现了微指令的十六进制代码表示和控制标志+下址表示的双向转换。此项目有两个生成目标：网页翻译器和命令行编译器。

## 构建

运行 Gradle 的 build task 即可。网页翻译器生成在 `docs` 文件夹下；命令行编译器暂时无法通过 Gradle 生成单一 jar 文件，访问 [Release](https://github.com/rewqazxv/HQFC-ucode/releases/) 页面下载预先用 IDEA 构建的版本。

## 网页翻译器

可通过 https://rewqazxv.github.io/HQFC-ucode/ 访问预先生成的页面。控制位标志以逗号分隔，不区分大小写，下划线和#号可忽略；字段存在表示该位为 1。指令列表中可以有任意数量的空格分隔。

## 命令行编译器

访问 [Release](https://github.com/rewqazxv/HQFC-ucode/releases/) 页面下载。

此编译器定义了一种微程序源代码格式，如下所示，每行有效的微代码由三个部分构成：地址、下址和控制标志列表，这三个部分之间用分号隔开。

```
addr; next; control_list
```

方便起见，源代码支持添加任意数量的空白及空行，可以在行尾多加一个分号结束。注释以 `//` 开始，到行尾结束；可以独占一行或是在行内使用。下例表现了一种合法的微程序源码形式，它定义了一个加法过程：

```
// ADD rx, ry
4; 5; LDDR1;                        // 暂存两寄存器输入
5; 6; ALU_BUS, S0, S3, CN#, LDER;   // 加法，结果保存到 ER
6; 0; WRD;                          // ER 写入寄存器 rx
```

这种形式恰好是分号分隔的 csv 表格的格式，因此可以[更改系统列表分隔符或导入文本文件](https://support.microsoft.com/zh-cn/office/%E5%AF%BC%E5%85%A5%E6%88%96%E5%AF%BC%E5%87%BA%E6%96%87%E6%9C%AC%EF%BC%88-txt-%E6%88%96-csv%EF%BC%89%E6%96%87%E4%BB%B6-5250ac4c-663c-47ce-937b-339e391393ba)，使用 Excel 进行编辑。

编译器使用：待编译源文件可以以参数的形式传入，或是运行后输入路径，如下。

```shell
$ java -jar ucode.jar source.csv
```

```shell
$ java -jar ucode.jar
Input file path: ...
```
