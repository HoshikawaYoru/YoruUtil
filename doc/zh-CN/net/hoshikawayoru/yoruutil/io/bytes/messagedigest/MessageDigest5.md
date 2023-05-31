## `MessageDigest`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.bytes.messagedigest.MessageDigest5`

这是一个MD5摘要类,使用此类可以快速的摘要MD5

### 方法
#### `digest()`
开始摘要
```java
public static byte[] download(byte[] bytes)
```
##### 参数
- `byte[] bytes` : 需要进行摘要的byte数组
##### 返回值
返回摘要后的byte数组

### 示例
以下是使用`MessageDigest`类摘要的示例：
```java
public class Main {
    public static void main(String[] args) {
        String str = "example";
        System.out.println(Arrays.toString(MessageDigest5.digest(str.getBytes())));
    }
}
```