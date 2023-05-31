## `Download`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.file.download.Download`

这是一个下载类，使用此类可以轻松地实现下载网络文件

### 方法
#### `download()`
开始下载文件
```java
public static void download(String fileUrl, File saveDir)
```
##### 参数
- `String fileUrl` : 下载文件的Url
- `saveDir` : 保存目录(会自动创建父目录和文件)

#### 异常
如果下载文件失败，将会抛出`IOException`

### 示例
以下是使用`Download`类下载文件的示例
```java
public class Main {
    public static void main(String[] args) {
        String downloadFileUrl = "https://example.com/file.zip";
        String saveDirectoryPath = "/path/to/save/directory";

        Download.download(downloadFileUrl, new File(saveDirectoryPath));
    }
}
```