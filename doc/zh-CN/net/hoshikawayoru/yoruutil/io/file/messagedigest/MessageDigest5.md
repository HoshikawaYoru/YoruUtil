## `MessageDigest`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.file.messagedigest.MessageDigest5`

这是一个文件MD5摘要类，使用此类可以快速进行文件MD5摘要

### 方法
#### `digest()`
开始摘要文件
```java
public static byte[] digest(File file)
```
##### 参数
- `File file` : 需要进行摘要的文件
##### 返回值
返回摘要后的byte数组

### 示例
以下是使用`MessageDigest`类摘要文件的示例：
```java
public class Main {
    public static void main(String[] args) {
        File file = new File("/path/example.txt");
        System.out.println(Arrays.toString(MessageDigest5.digest(file)));
    }
}
```