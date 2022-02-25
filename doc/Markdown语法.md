## 语法

### 注释

 Comments will not show up in preview

 %%
 comments go here

 %%

### 分页

<div STYLE="page-break-after: always;"></div>  

将这个插入到你要分页的地方

### 内嵌

https://publish.obsidian.md/help/How+to/Embed+files

!\[\[My File.pdf#page=number\]\]


<iframe
    border=0
    frameborder=0
    height=250
    width=550  
    src="https://twitframe.com/show?url=https%3A%2F%2Ftwitter.com%2Fjack%2Fstatus%2F20">
</iframe>


<iframe src="https://player.bilibili.com/player.html?aid=586848024&bvid=BV1Kz4y1m74W&cid=300166684&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> 
</iframe>

### 公式示例

$(log_{10}N)^2$

$tmp1/10 == 10^{k-2}$

$C_n^2$  

$\frac{expr1}{expr2}$

$\sqrt[x]{y}$  
$\sqrt{2}$

$\sum \frac{1}{i^2}$

$$\begin{matrix}
1&0&0\\
0&1&0\\
0&0&1\\
\end{matrix}$$

$$\begin{bmatrix}
{a_{11}}&{a_{12}}&{\cdots}&{a_{1n}}\\
{a_{21}}&{a_{22}}&{\cdots}&{a_{2n}}\\
{\vdots}&{\vdots}&{\ddots}&{\vdots}\\
{a_{m1}}&{a_{m2}}&{\cdots}&{a_{mn}}\\
\end{bmatrix}$$

$$\begin{cases}
a_1x+b_1y+c_1z=d_1\\
a_2x+b_2y+c_2z=d_2\\
a_3x+b_3y+c_3z=d_3\\
\end{cases}$$

$Sum=\begin{cases} L == 0 & H[R]\\
L != 0 & H[R] = H[L-1]\end{cases}$

$\begin{cases}a & x = 0\\ 
b & x > 0\end{cases}$

$$\begin{array}[b]{|c|c|} 
\hline 第一列 & 第二列& 第三列 \\ 
\hline 表格内容过长 & {当表格中要表达的内容比较多时，\\
页面上显示的表格会非常小，\\
看不清楚，如图所示，} & 所以要使用换行的办法 \\ 
\hline 
\end{array}
$$

## 常用公式

### **数学符号**

|  数学符号  |        算式         |  Markdown   |
| :--------: | :-----------------: | :---------: |
|  **上标**  |       $x^2 $        |     x^2     |
|  **下标**  |       $y_1 $        |     y_1     |
|  **组合**  | ${16}_{8}O{2+}_{2}$ |     {}      |
|  **分式**  |       $1/2 $        |     1/2     |
|            |   $\frac{1}{2} $    | \frac{1}{2} |
|            |    ${1}\over{2}$    |    \over    |
|  **矢量**  |      $\vec{a}$      |   \vec{a}   |
| **省略号** |      $\cdots$       |   \cdots    |

**花括号**
$$
c(u)=\begin{cases} \sqrt\frac{1}{N},u=0\\ \sqrt\frac{2}{N}, u\neq0\end{cases}
$$
**矩阵**
$$
a=\left[
\matrix{
\alpha_1 & test1\\
\alpha_2 & test2\\
\alpha_3 & test3
}
\right]
$$

### **关系运算符**

| 运算 |   符号   | Markdown |
| ---- | :------: | :------: |
| 加减 |  $\pm $  |   \pm    |
| 减加 |  $\mp$   |   \mp    |
| 乘法 | $\times$ |  \times  |
| 星乘 |  $\ast$  |   \ast   |
| 点乘 | $\cdot$  |  \cdot   |
| 除法 |  $\div$  |   \div   |

### **逻辑运算**

| 运算       |   符号    | Markdown |
| ---------- | :-------: | :------: |
| 小于等于   |  $\leq$   |   \leq   |
| 大于等于   |  $\geq$   |   \geq   |
| 不等于     |  $\neq$   |   \neq   |
| 不大于等于 |  $\ngeq$  |  \ngeq   |
| 不小于等于 |  $\nleq$  |  \nleq   |
| 约等于     | $\approx$ | \approx  |
| 恒定等于   | $\equiv$  |  \equiv  |

### **高级运算**

|     数学符号     |                            算式                            |          Markdown          |
| :--------------: | :--------------------------------------------------------: | :------------------------: |
|    **平均数**    |                      $\overline{xyz}$                      |        \overline{}         |
|   **三角函数**   |                           $\sin$                           |            \sin            |
|   **对数函数**   |                           $\ln2$                           |            \ln2            |
|                  |                         $\log_28$                          |          \log_28           |
|                  |                          $\lg10$                           |           \lg10            |
|   **开方运算**   |                      $\sqrt[3]{x+y}$                       |  \sqrt[开方数]{被开方数}   |
| **开二次方运算** |                         $\sqrt{2}$                         |          \sqrt{2}          |
|     **积分**     |                        $\int{x}dx$                         |         \int{x}dx          |
|                  |                    $\int_{1}^{2}{x}dx$                     |     \int_{1}^{2}{x}dx      |
|     **微分**     |              $\frac{\partial x}{\partial y}$               |          \partial          |
|     **极限**     |                          $lima+b$                          |         \lim{a+b}          |
|                  |                $\lim_{n\rightarrow+\infty}$                | \lim_{n\rightarrow+\infty} |
|                  | $\displaystyle \lim^{x \to \infty}_{y \to 0}{\frac{x}{y}}$ |     \displaystyle \lim     |
|     **累加**     |                         $\sum{a}$                          |          \sum{a}           |
|                  |                  $\sum_{n-1}^{100}{a_n}$                   |   \sum_{n-1}^{100}{a_n}    |
|                  | $\displaystyle \sum^{x \to \infty}_{y \to 0}{\frac{x}{y}}$ |     \displaystyle \sum     |
|     **累乘**     |                         $\prod{x}$                         |          \prod{x}          |
|                  |                  $\prod_{n=1}^{99}{x_n}$                   |   \prod_{n=1}^{99}{x_n}    |

### 集合运算

1. 属于运算，符号：`\in`，如：$x \in y$
2. 不属于运算，符号：`\notin`，如：$x \notin y$
3. 不属于运算，符号：`\not\in`，如：$x \not\in y$
4. 子集运算，符号：`\subset`，如：$x \subset y$
5. 子集运算，符号：`\supset`，如：$x \supset y$
6. 真子集运算，符号：`\subseteq`，如：$x \subseteq y$
7. 非真子集运算，符号：`\subsetneq`，如：$x \subsetneq y$
8. 真子集运算，符号：`\supseteq`，如：$x \supseteq y$
9. 非真子集运算，符号：`\supsetneq`，如：$x \supsetneq y$
10. 非子集运算，符号：`\not\subset`，如：$x \not\subset y$
11. 非子集运算，符号：`\not\supset`，如：$x \not\supset y$
12. 并集运算，符号：`\cup`，如：$x \cup y$
13. 交集运算，符号：`\cap`，如：$x \cap y$
14. 差集运算，符号：`\setminus`，如：$x \setminus y$
15. 同或运算，符号：`\bigodot`，如：$x \bigodot y$
16. 同与运算，符号：`\bigotimes`，如：$x \bigotimes y$
17. 实数集合，符号：`\mathbb{R}`，如：`\mathbb{R}`
18. 自然数集合，符号：`\mathbb{Z}`，如：`\mathbb{Z}`
19. 空集，符号：`\emptyset`，如：$\emptyset$

### 数学符号

1. 无穷，符号：`\infty`，如：$\infty$
2. 虚数，符号：`\imath`，如：$\imath$
3. 虚数，符号：`\jmath`，如：$\jmath$
4. 数学符号，符号`\hat{a}`，如：$\hat{a}$
5. 数学符号，符号`\check{a}`，如：$\check{a}$
6. 数学符号，符号`\breve{a}`，如：$\breve{a}$
7. 数学符号，符号`\tilde{a}`，如：$\tilde{a}$
8. 数学符号，符号`\bar{a}`，如：$\bar{a}$
9. 矢量符号，符号`\vec{a}`，如：$\vec{a}$
10. 数学符号，符号`\acute{a}`，如：$\acute{a}$
11. 数学符号，符号`\grave{a}`，如：$\grave{a}$
12. 数学符号，符号`\mathring{a}`，如：$\mathring{a}$
13. 一阶导数符号，符号`\dot{a}`，如：$\dot{a}$
14. 二阶导数符号，符号`\ddot{a}`，如：$\ddot{a}$
15. 上箭头，符号：`\uparrow`，如：$\uparrow$
16. 上箭头，符号：`\Uparrow`，如：$\Uparrow$
17. 下箭头，符号：`\downarrow`，如：$\downarrow$
18. 下箭头，符号：`\Downarrow`，如：$\Downarrow$
19. 左箭头，符号：`\leftarrow`，如：$\leftarrow$
20. 左箭头，符号：`\Leftarrow`，如：$\Leftarrow$
21. 右箭头，符号：`\rightarrow`，如：$\rightarrow$
22. 右箭头，符号：`\Rightarrow`，如：$\Rightarrow$
23. 底端对齐的省略号，符号：`\ldots`，如：$1,2,\ldots,n$
24. 中线对齐的省略号，符号：`\cdots`，如：$x_1^2 + x_2^2 + \cdots + x_n^2$
25. 竖直对齐的省略号，符号：`\vdots`，如：$\vdots$
26. 斜对齐的省略号，符号：`\ddots`，如：$\ddots$

### **汉字、字体与格式**

| 意义       | 符号                                               | Markdown                     |
| ---------- | -------------------------------------------------- | ---------------------------- |
| 汉字形式   | $V_{\mbox{初始}}$                                  | \mbox{}                      |
| 字体变大   | $\displaystyle \frac{x+y}{y+z}$                    | \displaystyle                |
| 下划线符号 | $\underline{x+y}$                                  | \underline                   |
| 标签       | $\tag{11}$                                         | \tag{数字}                   |
| 上大括号   | $\overbrace{a+b+c+d}^{2.0}$                        | \overbrace{算式}             |
| 下大括号   | $\underbrace{b+c}_{1.0}+d$                         | \underbrace{算式}            |
| 上位符号   | $\vec{x}\stackrel{\mathrm{def}}{=}{x_1,\dots,x_n}$ | \stacrel{上位符号}{基位符号} |

### **占位符**

| 意义         | 符号         | Markdown |
| ------------ | ------------ | -------- |
| 两个quad空格 | $x \qquad y$ | \qquad   |
| quad空格     | $x \quad y$  | \quad    |
| 大空格       | $x\ y$       | \        |
| 中空格       | $x \, y$     | \:       |
| 小括号       | $x\,y$       | \,       |
| 紧贴         | $x\!y$       | \!       |

### **定界符与组合**

| 意义       | 符号                                                         | Markdown                                          |
| ---------- | ------------------------------------------------------------ | ------------------------------------------------- |
| 括号       | $()\big(\big) \Big(\Big) \bigg(\bigg) \Bigg(\Bigg)$          | ()\big(\big) \Big(\Big) \bigg(\bigg) \Bigg(\Bigg) |
| 中括号     | $[]$                                                         | []                                                |
| 大括号     | $\{\}$                                                       | \{\}                                              |
| 自适应括号 | $\left(x\right)$，$\left(x{yz}\right)$                       | \left \right                                      |
| 组合公式   | ${n+1 \choose k}={n \choose k}+{n \choose k-1}$              | {上位公式 \choose 下位公式}                       |
|            | $\sum_{k_0,k_1,\ldots>0 \atop k_0+k_1+\cdots=n}A_{k_0}A_{k_1}\cdots$ | {上位公式 \atop 下位公式}                         |

### **特殊字符**

|     符号     |  Markdown  |
| :----------: | :--------: |
|  $\forall$   |  \forall   |
|   $\infty$   |   \infty   |
| $\emptyset$  | \emptyset  |
|  $\exists$   |  \exists   |
|   $\nabla$   |   \nabla   |
|    $\bot$    |    \bot    |
|   $\angle$   |   \angle   |
|  $\because$  |  \because  |
| $\therefore$ | \therefore |

### **希腊字母**

| 大写       | Markdown | 小写          | Markdown    |
| ---------- | -------- | ------------- | ----------- |
| $A$        | A        | $\alpha$      | \alpha      |
| *B*        | B        | $\beta$       | \beta       |
| Γ          | \Gamma   | γ \gamma*γ*   | \gamma      |
| Δ          | \Delta   | δ \delta*δ*   | \delta      |
| *E*        | E        | ϵ \epsilon*ϵ* | \epsilon    |
|            |          | *ε*           | \varepsilon |
| *Z*        | Z        | ζ \zeta*ζ*    | \zeta       |
| *H*        | H        | η \eta*η*     | \eta        |
| Θ          | \Theta   | θ \theta*θ*   | \theta      |
| *I*        | I        | ι \iota*ι*    | \iota       |
| *K*        | K        | κ \kappa*κ*   | \kappa      |
| $\Lambda$  | \Lambda  | $\lambda$     | \lambda     |
| *M*        | M        | μ \mu*μ*      | \mu         |
| *N*        | N        | ν \nu*ν*      | \nu         |
| Ξ          | \Xi      | ξ \xi*ξ*      | \xi         |
| *O*        | O        | ο \omicron*ο* | \omicron    |
| $\Pi$      | \Pi      | $\pi$         | \pi         |
| *P*        | P        | ρ \rho*ρ*     | \rho        |
| Σ          | \Sigma   | σ \sigma*σ*   | \sigma      |
| *T*        | T        | τ \tau*τ*     | \tau        |
| $\Upsilon$ | \Upsilon | *υ*           | \upsilon    |
| Φ          | \Phi     | ϕ \phi*ϕ*     | \phi        |
|            |          | *φ*           | \varphi     |
| *X*        | X        | χ \chi*χ*     | \chi        |
| Ψ          | \Psi     | ψ \psi*ψ*     | \psi        |
| Ω          | \Omega   | ω \omega*ω*   | \omega      |

## 其它

https://zhuanlan.zhihu.com/p/95886235

### 文本块引用

输入 \[\[^\]\] 本文的块,  \[\[^^\]\] 引用全库的块

### tag query

```query
tag:字典序


```

### tag nest

#动态规划/一个样本做行一个样本做列