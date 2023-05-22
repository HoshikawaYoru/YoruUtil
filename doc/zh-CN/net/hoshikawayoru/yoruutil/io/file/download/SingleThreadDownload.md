## SingleThreadDownload类
> 此类位于 net.hoshikawayoru.yoruutil.io.file.download.SingleThreadDownload

这是一个下载类，使用此类可以轻松地实现下载网络文件

### 构造函数

```java
public SingleThreadDownload(String fileUrl, String saveDir)
```

#### 参数

- fileUrl：要下载的文件的URL地址
- saveDir：要保存下载文件的目录路径

### 方法

#### `download()`

调用此方法可以开始下载文件

```java
public void download()
```

#### `getFileName()`

获取下载文件的文件名

```java
public String getFileName()
```

#### `getFileSize()`

获取下载文件的长度

```java
public long getFileSize()
```

#### `getDownloadTime()`

获取下载文件所用的时间

```java
public double getDownloadTime()
```

#### 异常
如果下载文件失败，将会抛出IOException异常

### 示例
以下是使用SingleThreadDownload类下载文件的示例：

```java
public static void main(String[] args) {
String downloadFileUrl = "https://example.com/file.zip";
String saveDirectoryPath = "/path/to/save/directory";

    SingleThreadDownload download = new SingleThreadDownload(downloadFileUrl, saveDirectoryPath);
    download.download();
    
    System.out.println("下载文件名：" + download.getFileName());
    System.out.println("下载文件长度：" + download.getFileSize());
    System.out.println("下载文件时间：" + download.getDownloadTime() + "秒");
    System.out.println("下载文件速度：" + download.getDownloadSpeed() + "KB/s");
}
```