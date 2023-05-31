## `MultiThreadDownload`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.file.download.aMultiThreadDownload`

这是一个多线程下载类，使用此类可以轻松地实现多线程下载网络文件

### 方法
#### `download()`
开始多线程下载文件
```java
public static void download(String fileUrl, File saveDir, int threadCount)
```
##### 参数
- `String fileUrl` : 下载文件的Url
- `File saveDir` : 保存目录(会自动创建父目录和文件)
- `int threadCount` : 使用的线程数量

#### 异常
如果下载文件失败，将会抛出`IOException`

### 示例
以下是使用`MultiThreadDownload`类下载文件的示例：
```java
public class Main {
    public static void main(String[] args) {
        String downloadFileUrl = "https://example.com/file.zip";
        String saveDirectoryPath = "/path/to/save/directory";

        Download.download(downloadFileUrl, new File(saveDirectoryPath), 5);
    }
}
```