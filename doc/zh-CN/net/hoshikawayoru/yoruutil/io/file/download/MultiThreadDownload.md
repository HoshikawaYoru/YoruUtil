## `MultiThreadDownload`类
> 此类位于 `net.hoshikawayoru.yoruutil.io.file.download.MultiThreadDownload`

这是一个多线程下载类，使用此类可以轻松地实现多线程下载网络文件

### 构造函数
```java
public MultiThreadDownload(String fileUrl, String saveDir, int threadCount)
```

#### 参数
- fileUrl:要下载的文件的URL地址
- saveDir:要保存下载文件的目录路径
- threadCount:

### 方法
#### `download()`
开始下载文件
```java
public void download()
```

#### `getFileUrl()`
获取文件Url
```java
public String getFileUrl()
```

#### `getSaveDir()`
获取保存目录
```java
public String getSaveDir()
```

#### `getThreadCount`
获取使用的线程数量
```java
public int getThreadCount()
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
设置文件Url
```java
public void setFileUrl(String fileUrl)
```

#### `setSaveUrl`
设置保存目录
```java
public void setSaveDir(String saveDir)
```

#### `setThreadCount`
设置下载使用的线程数量
```java
public void setThreadCount(int threadCount);
```

#### 异常
如果下载文件失败，将会抛出IOException异常

### 示例
以下是使用MultiThreadDownload类下载文件的示例：
```java
public static void main(String[] args) {
    String downloadFileUrl = "https://example.com/file.zip";
    String saveDirectoryPath = "/path/to/save/directory";

    SingleThreadDownload download = new SingleThreadDownload(downloadFileUrl, saveDirectoryPath, 5);
    download.download();
}
```