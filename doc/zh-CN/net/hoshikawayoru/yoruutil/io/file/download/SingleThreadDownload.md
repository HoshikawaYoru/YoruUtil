## `SingleThreadDownload`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.file.download.SingleThreadDownload`

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

#### `getFileUrl`
获取下载文件Url
```java
public long getFileUrl()
```

#### `getSaveDir`
获取保存目录
```java
public String getSaveDir()
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

#### `setFileUrl`
设置下载文件Url
```java
public void setFileUrl(String fileUrl)
```

#### `setSaveDir`
设置保存目录
```java
public void setSaveDir(String saveDir)
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
}
```