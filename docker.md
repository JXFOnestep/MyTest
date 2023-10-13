# docker基础篇

## 概述

### 容器较为官方的解释

**一句话概括容器：容器就是将软件打包成标准化单元，以用于开发、交付和部署。**

* 容器镜像是轻量的、可执行的独立软件包 ，包含软件运行所需的所有内容：代码、运行时环境、系统工具、系统库和设置。
* 容器化软件适用于基于 Linux 和 Windows 的应用，在任何环境中都能够始终如一地运行。
* 容器赋予了软件独立性，使其免受外在环境差异（例如，开发和预演环境的差异）的影响，从而有助于减少团队间在相同基础设施上运行不同软件时的冲突
  

### 容器较为通俗的解释

​	如果需要通俗地描述容器的话，我觉得容器就是一个存放东西的地方，就像书包可以装各种文具、衣柜可以放各种衣服、鞋架可以放各种鞋子一样。我们现在所说的容器存放的东西可能更偏向于应用比如网站、程序甚至是系统环境。

![image-20220305100542133](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220305100542133.png)

## 虚拟化技术和容器化技术

### 虚拟化技术

首先，Docker **容器虚拟化**技术为基础的软件，那么什么是虚拟化技术呢？

简单点来说，虚拟化技术可以这样定义：

> 虚拟化技术是一种资源管理技术，是将计算机的各种实体资源（CPU、内存、磁盘空间、网络适配器等），予以抽象、转换后呈现出来并可供分割、组合为一个或多个电脑配置环境。由此，打破实体结构间的不可切割的障碍，使用户可以比原本的配置更好的方式来应用这些电脑硬件资源。这些资源的新虚拟部分是不受现有资源的架设方式，地域或物理配置所限制。一般所指的虚拟化资源包括计算能力和数据存储。

### Docker 基于 LXC 虚拟容器技术

Docker 技术是基于 LXC（[Linux](https://so.csdn.net/so/search?q=Linux&spm=1001.2101.3001.7020) container- Linux 容器）虚拟容器技术的

……



## Docker基本组成

**Docker 中有非常重要的三个基本概念，理解了这三个概念，就理解了 Docker 的整个生命周期。**

- **镜像（Image）**
- **容器（Container）**
- **仓库（Repository）**



## Docker安装

### 使用官方安装脚本自动安装

```sh
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
```



### 手动安装

#### 查看系统内核和系统信息

```sh
uname -r     #查看系统内核版本
cat /etc/os-release  #查看系统版本
```

#### 卸载旧版本

命令：

```sh
sudo apt-get remove docker docker-engine docker.io containerd runc /var/lib/docke
sudo apt-get purge docker
sudo apt-get purge docker-ce
sudo apt-get remove -y docker-*
```

#### 下载依赖安装包

1）`sudo apt-get update`

2）允许apt通过https使用repository安装软件包 ``

```armasm
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
```

3）添加Docker官方GPG key

```
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

（国内阿里云版 sudo `curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | apt-key add -`）

4）验证key的指纹

　　`sudo apt-key fingerprint 0EBFCD88`

正常输出为：

pub  rsa4096 2017-02-22 [SCEA]
   9DC8 5822 9FC7 DD38 854A E2D8 8D81 803C 0EBF CD88
uid      [ unknown] Docker Release (CE deb) <docker@docker.com>
sub  rsa4096 2017-02-22 [S]

5）添加稳定版repository

```dockerfile
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```

国内阿里云版：

```shell
sudo add-apt-repository \
   "deb [arch=amd64] https://mirrors.aliyun.com/docker-ce/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```

5）`sudo apt-get update`

6）安装最新版本的docker ce和containerd

```csharp
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

（如果您启用了多个Docker存储库，则在apt-get install或apt-get update命令中未指定版本的情况下安装或更新将始终安装尽可能高的版本）

7）验证： docker --version

 ```sh
 sudo docker run hello-world
 ```



## Docker 常用命令

### 基本命令

```sh
docker version          #查看docker的版本信息
docker info             #查看docker的系统信息,包括镜像和容器的数量
docker 命令 --help       #帮助命令(可查看可选的参数)
docker COMMAND --help
```

### 镜像命令

#### **docker images** 查看本地主机的所有镜像

```sh
[root@iZ1608aqb7ntn9Z /]# docker images
REPOSITORY    TAG       IMAGE ID       CREATED        SIZE
hello-world   latest    d1165f221234   5 months ago   13.3kB
```

参数列表介绍

```sh
# 解释:
1.REPOSITORY  镜像的仓库源
2.TAG  镜像的标签
3.IMAGE ID 镜像的id
4.CREATED 镜像的创建时间
5.SIZE 镜像的大小
# 可选参数
-a/--all 列出所有镜像
-q/--quiet 只显示镜像的id
```

#### **docker search** 搜索镜像

示例

```sh
docker search mysql
```

```sh
#可选参数
Search the Docker Hub for images
Options:
  -f, --filter filter   Filter output based on conditions provided
      --format string   Pretty-print search using a Go template
      --limit int       Max number of search results (default 25)
      --no-trunc        Don't truncate output
            
#搜索收藏数大于3000的镜像
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker search mysql --filter=STARS=3000
NAME      DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
mysql     MySQL is a widely used, open-source relation…   10308     [OK]
mariadb   MariaDB is a community-developed fordockerk of MyS…   3819      [OK]

```

####  **docker pull 镜像名[:tag]** 下载镜像

示例

```sh
[root@iZ1608aqb7ntn9Z /]# docker pull mysql:5.7
5.7: Pulling from library/mysql
33847f680f63: Pull complete 
5cb67864e624: Pull complete 
1a2b594783f5: Pull complete 
b30e406dd925: Pull complete 
48901e306e4c: Pull complete 
603d2b7147fd: Pull complete 
802aa684c1c4: Pull complete 
5b5a19178915: Pull complete 
f9ce7411c6e4: Pull complete 
f51f6977d9b2: Pull complete 
aeb6b16ce012: Pull complete 
Digest: sha256:be70d18aedc37927293e7947c8de41ae6490ecd4c79df1db40d1b5b5af7d9596
Status: Downloaded newer image for mysql:5.7
docker.io/library/mysql:5.7

```

#### **docker rmi** 删除镜像

```sh
#1.删除指定的镜像id
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker rmi -f  镜像id
#2.删除多个镜像id
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker rmi -f  镜像id 镜像id 镜像id
#3.删除全部的镜像id
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker rmi -f  $(docker images -aq)
```

### 容器命令

#### docker run [可选参数] image 运行容器

```sh
docker run [可选参数] image

#参数说明
--name="名字"           指定容器名字
-d                     后台方式运行
-i					   交互式（通常要-it一同使用）
-t					   分配一个虚拟的终端
-it                    使用交互方式运行,进入容器查看内容
-p                     指定容器的端口
( -p ip:主机端口:容器端口  配置主机端口映射到容器端口
  -p 主机端口:容器端口
  -p 容器端口)
-P                     随机指定端口(大写的P)

```

#### 进入容器

 -it 交互式，进入容器

```sh
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker run -it [容器ID] /bin/bash 
```



#### exit 退出容器

```sh
#exit 停止并退出容器（后台方式运行则仅退出）
#Ctrl+P+Q  不停止容器退出
[root@bd1b8900c547 /]# exit
exit
[root@iZwz99sm8v95sckz8bd2c4Z ~]#
```



#### docker ps 列出容器

```sh
#docker ps 
     # 列出当前正在运行的容器
-a   # 列出所有容器的运行记录
-n=? # 显示最近创建的n个容器
-q   # 只显示容器的编号


[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
[root@iZwz99sm8v95sckz8bd2c4Z ~]# docker ps -a
CONTAINER ID   IMAGE          COMMAND       CREATED         STATUS                     PORTS     NAMES
bca129320bb5   centos         "/bin/bash"   4 minutes ago   Exited (0) 3 minutes ago             optimistic_shtern
bd1b8900c547   centos         "/bin/bash"   6 minutes ago   Exited (0) 5 minutes ago             cool_tesla
cf6adbf1b506   bf756fb1ae65   "/hello"      5 hours ago     Exited (0) 5 hours ago               optimistic_darwin

```

#### 删除容器

```sh
docker rm 容器id                 #删除指定的容器,不能删除正在运行的容器,强制删除使用 rm -f
docker rm -f $(docker ps -aq)   #删除所有的容器
docker ps -a -q|xargs docker rm #删除所有的容器

```

#### 启动和重启容器

```sh
docker start 容器id          #启动容器
docker restart 容器id        #重启容器
docker stop 容器id           #停止当前运行的容器
docker kill 容器id           #强制停止当前容器
```

#### 保存修改后的容器为另一个镜像

```
docker commit 75eb300dd5b2 ubuntu:2.0
```

\# 75eb300dd5b2为容器id



### 其他命令

1. 查看日志 *docker logs --help*
2. 查看容器中进程信息 *docker top c703b5b1911f*
3. 查看容器的元数据 *docker inspect 容器id*

4. 进入当前正在运行的容器 *docker exec -it c703b5b1911f /bin/bash* 或者 *docker attach c703b5b1911f*

   docker exec 进入容器后开启一个新的终端，可以在里面操作

   docker attach 进入容器正在执行的终端，不会启动新的进程

### 常见容器部署 Nginx、Tomcat、ES

​	……



## 容器和镜像的关系

​	容器在启动或者创建时，必须指定一个镜像的名称或者 id ，其实，这时镜像所扮演的角色就是容器的模版，**不同的镜像可以构造出不同的容器，同一个镜像，我们也可以通过配置不同参数来构造出不同的容器**。如下命令：

```sh
docker run -itd --name nginx nginx
```

​	命令中的最后一个 nginx 即表示创建该容器所需要的镜像（模版），当然这里还省略了一些信息，例如版本号等。



比如：通过 *docker run -it [image_id] /bin/bash*，运行一个容器，然后新开一个终端，运行相同命令，会发现当前运行的两个终端为不同的容器，这就体现了同一个镜像，可以构造出不同的容器，可以通过 *docker exec -it [container_id] /bin/bash*进入同一个容器。





---





# docker进阶篇

## Docker镜像详解

### UnionFS（联合文件系统）

* 联合文件系统（UnionFS）是一种分层、轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下。联合文件系统是 Docker 镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像。
* 特性：一次同时加载多个文件系统，但从外面看起来只能看到一个文件系统。联合加载会把各层文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录。

### 镜像加载原理

​	Docker的镜像实际由一层一层的文件系统组成：

* bootfs（boot file system）主要包含bootloader和kernel。bootloader主要是引导加载kernel，完成后整个内核就都在内存中了。此时内存的使用权已由bootfs转交给内核，系统卸载bootfs。可以被不同的Linux发行版公用。
* rootfs（root file system），包含典型Linux系统中的/dev，/proc，/bin，/etc等标准目录和文件。rootfs就是各种不同操作系统发行版（Ubuntu，Centos等）。因为底层直接用Host的kernel，rootfs只包含最基本的命令，工具和程序就可以了。
* 分层理解
  所有的Docker镜像都起始于一个基础镜像层，当进行修改或增加新的内容时，就会在当前镜像层之上，创建新的容器层。
  容器在启动时会在镜像最外层上建立一层可读写的容器层（R/W），而镜像层是只读的（R/O）。



```sh
docker commit -m "描述信息" -a "作者" 容器名 目标镜像名:[tag]  # 编辑容器后提交容器成为一个新镜像
```



## DockerFile

Dockerfile是用来构建docker镜像的文件

### 构建步骤

编写一个dockerfile文件,随后运行命令：

```sh
docker build -f 文件路径 -t 镜像名 .  # 文件名为Dockerfile时可省略且最后的.不要忽略
docker run     # 运行镜像
docker push    # 发布镜像
```

示例

```sh
[root@iZ1608aqb7ntn9Z 20210806]# vim Dockerfile 
# ----------写入内容-----------------
FROM centos      # 来自centos
CMD /bin/bash    # 进入到/bin/bash
CMD echo Hello Dockerfile   # 输出Hello Dockerfile
# ----------写入结束-----------------
[root@iZ1608aqb7ntn9Z 20210806]# docker build -f ./Dockerfile -t mydocker .
Sending build context to Docker daemon   2.56kB
Step 1/3 : FROM centos
 ---> 300e315adb2f
Step 2/3 : CMD /bin/bash
 ---> Running in 526f489adf0b
Removing intermediate container 526f489adf0b
 ---> 3c2af9c73098
Step 3/3 : CMD echo Hello Dockerfile
 ---> Running in 023af54a93e2
Removing intermediate container 023af54a93e2
 ---> 7753b44c9137
Successfully built 7753b44c9137
Successfully tagged mydocker:latest
[root@iZ1608aqb7ntn9Z 20210806]# docker images
REPOSITORY            TAG       IMAGE ID       CREATED          SIZE
mydocker              latest    7753b44c9137   6 seconds ago    209MB
......
[root@iZ1608aqb7ntn9Z 20210806]# docker run -it mydocker
Hello Dockerfile
```

### Dockerfile命令

| 命令           |                           **效果**                           |
| -------------- | :----------------------------------------------------------: |
| FROM           | 指定基础镜像，一切都是从这里开始构建，基础镜像：Centos/Ubuntu |
| MAINTAINER     |                指定维护者信息，镜像作者+邮箱                 |
| RUN            |                 镜像构建的时候需要运行的命令                 |
| ADD            |                   为镜像添加内容（压缩包）                   |
| WORKDIR        |               镜像工作目录（进入容器时的目录）               |
| VOLUME         |                          挂载的目录                          |
| EXPOSE         |                         保留端口配置                         |
| CMD/ENTRYPOINT | 指定这个容器启动时要运行的命令（CMD替代先前命令，ENTRYPOINT在先前命令后追加） |
| COPY           |                类似于ADD，将文件拷贝到镜像中                 |
| ENV            |                      构建时设置环境变量                      |

**COPY时src必须是 build的上下文目录（Dockerfile同级目录或子目录），不能是父目录或者绝对路径**

### 构建过程

- 每个保留关键字（指令）都必须是大写字母
- 从上到下顺序执行
- “#” 表示注释
- 每一个指令都会创建提交一个新的镜像层并提交



### 构建实例1

第二步：编写dockerfile

```sh
# 创建一个文件夹，并进入
mkdir docker-test-volume
cd docker-test-volume

# 在该文件夹中创建Dockerfile文件
vim Dockerfile01
```

文件内容如下

```sh
# 文件中的内容，指令需要大写
FROM  ubuntu                       # 用什么作为基础

RUN echo "hello world"			   # 镜像构建时需要运行的命令
RUN mkdir test && cd test && touch first.cpp
								   # 创建文件夹test，在里面创建first.cpp的文件

CMD echo "-------end-------"       # 生成完之后发一段消息

CMD /bin/bash                      # 生成完之后默认是走的控制台

# 这里的每个命令就是镜像的一层
```

第二步：构建脚本生成镜像

> docker build -f /home/jxf/docker-test-volume/dockerfile01 -t jxf/ubuntu:1.0 .
>
> \# -f   通过什么文件来构建，后面接构建文件的地址 
>
> \# -t   生成文件的版本

第三步：启动自己的容器

```sh
# 交互式启动容器
docker run -it db89686baa5c  /bin/bash  # id 通过docker images查看
# 进入容器可以看到根目录有test文件夹，并且里面有 test.cpp这个文件，这正是上面run要做的内容
```







### 构建实例2 (jdk+tomcat)

```sh
[root@iZ1608aqb7ntn9Z 20210806]# vim DockerFile2
# -----------写入文件--------------

FROM centos
  
COPY ymx /opt/Docker/20210806/ymx

ADD jdk8.tar.gz /usr/local
ADD tomcat.tar.gz /usr/local

RUN yum -y install vim

ENV MYPATH /usr/local
WORKDIR $MYPATH

ENV JAVA_HOME /usr/local/jdk1.8.0_141
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH $PATH:$JAVA_HOME/bin

EXPOSE 8080

# -----------写入文件完成--------------
[root@iZ1608aqb7ntn9Z 20210806]# ls
Dockerfile  DockerFile2  ymx
[root@iZ1608aqb7ntn9Z 20210806]# cp /tmp/jdk8.tar.gz jdk8.tar.gz
[root@iZ1608aqb7ntn9Z 20210806]# cp /tmp/tomcat.tar.gz tomcat.tar.gz
[root@iZ1608aqb7ntn9Z 20210806]# ls
Dockerfile  DockerFile2  jdk8.tar.gz  tomcat.tar.gz  ymx
[root@iZ1608aqb7ntn9Z 20210806]# docker build -f ./DockerFile2 -t mytomcat9 . 
Sending build context to Docker daemon    197MB
Step 1/11 : FROM centos
......
Successfully built 86a9a8dd939a
Successfully tagged mytomcat9:latest
[root@iZ1608aqb7ntn9Z 20210806]# docker images
REPOSITORY            TAG       IMAGE ID       CREATED             SIZE
mytomcat9             latest    86a9a8dd939a   26 seconds ago      667MB
......
[root@iZ1608aqb7ntn9Z 20210806]# docker run -it mytomcat9 /bin/bash
[root@ed5fd71834e2 local]# ls
apache-tomcat-9.0.44  bin  etc  games  include  jdk1.8.0_141  lib  lib64  libexec  sbin  share  src
[root@ed5fd71834e2 local]# java -version
java version "1.8.0_141"
Java(TM) SE Runtime Environment (build 1.8.0_141-b15)
Java HotSpot(TM) 64-Bit Server VM (build 25.141-b15, mixed mode)

```



## docker**清除缓存**

**docker system prune --volumes**



**docker system prune --all  清除所有缓存**





## 参考文献

 https://blog.csdn.net/weixin_43246215/article/details/108934216





# docker-compose

## 概述

Docker Compose是一个用来定义和运行复杂应用的Docker工具。一个使用Docker容器的应用，通常由多个容器组成。使用Docker Compose不再需要使用shell脚本来启动容器。 
Compose 通过一个配置文件来管理多个Docker容器，在配置文件中，所有的容器通过services来定义，然后使用docker-compose脚本来启动，停止和重启应用，和应用中的服务以及所有依赖服务的容器，非常适合组合使用多个容器进行开发的场景。

**docker和compose的兼容性，具体查看官网**

## 安装

### 从github上下载docker-compose二进制文件安装

* 下载最新版的docker-compose文件

  ```sh
  sudo curl -L https://github.com/docker/compose/releases/download/1.16.1/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
  ```

* 添加可执行权限

  ```sh
  sudo chmod +x /usr/local/bin/docker-compose
  ```

* 测试安装结果

  ```sh
  $ docker-compose --version
   
  docker-compose version 1.16.1, build 1719ceb
  ```



### pip 安装

```sh
sudo pip install docker-compose
```



## docker-compose文件结构

docker-compose.yml

```yaml
version: '3'
services:
  back:
    image: backService:1.0
    container_name: back
    environment:
      - name=tom
      - DB_PATH=jdbc:sqlite:/data/ns.db
    restart: always
    privileged: true
    ports:
      - "9000:9000"
    networks:
      - "net"
    volumes:
      - "/root/k3s.kube.config:/k3s.kube.config"
      - "/root/data:/data"
      - "/etc/network/interfaces:/etc/network/interfaces"
  front:
    image: front:1.0
    container_name: front
    restart: always
    ports:
      - "10087:80"
    networks:
      - "net"
    volumes:
      - "/root/nginx.conf:/etc/nginx/nginx.conf"
networks:
  net:
    driver: bridge
```

**version**：指定 docker-compose.yml 文件的写法格式

**services**：多个容器集合environment：环境变量配置，可以用数组或字典两种方式

```yaml
    environment:
        RACK_ENV: "development"
        SHOW: "ture"
    -------------------------
    environment:
        - RACK_ENV="development"
        - SHOW="ture"
```

**image**：指定服务所使用的镜像

```yaml
    version: '2'
    services:
      redis:
        image: redis:alpine
```

**expose**：定义容器用到的端口（一般用来标识镜像使用的端口，方便用ports映射）

```yaml
    expose:
        - "3000"
        - "8000"
```

**ports**：定义宿主机端口和容器端口的映射，可使用宿主机IP+宿主机端口进行访问 宿主机端口:容器端口

```yaml
    ports:   # 暴露端口信息  - "宿主机端口:容器暴露端口"
    - "5000"
    - "8081:8080"

    # 注：仅指定容器端口时，宿主机将会随机选择端口 
```

**volumes**：卷挂载路径，定义宿主机的目录/文件和容器的目录/文件的映射  宿主机路径:容器路径

```yaml
    volumes:
      - "/var/lib/mysql"
      - "hostPath:containerPath"
      - "root/configs:/etc/configs"
```

**depend_on**: 规定service加载顺序，例如数据库服务需要在后台服务前运行

**extra_hosts**：类似于docker里的--add-host参数 配置DNS域名解析(域名和IP的映射)

```yaml
	extra_hosts:
 		- "googledns:8.8.8.8"
 		- "dockerhub:52.1.157.61"
```

相当于在容器种的/etc/hosts文件里增加

```yaml
    8.8.8.8 googledns
    52.1.157.61 dockerhub
```

**restart: always** ：配置重启，docker每次启动时会启动该服务

**privileged: true** ：开启特权模式

**user: root** ：指定容器运行的用户名

**links**：将指定容器连接到当前连接，可以设置别名，已废弃，推荐使用networks

**logging**：日志服务

```yaml
    logging:
      driver: syslog # driver：默认json-file，可选syslog
      options:
        syslog-address: "tcp://192.168.0.42:123"
```

**driver**：默认json-file，可选syslog

```yaml
    network_mode: "bridge"
    network_mode: "host"
    network_mode: "none"
    network_mode: "service:[service name]"
    network_mode: "container:[container name/id]"
```



## docker-compose 命令

> **注意：**
>
> 以下都需要在docker-compose.yml所在目录下执行，且名字就是默认的docker-compose.yml，否则需要加上 -f yml地址
>
> 如：  docker-compose -f /usr/docker/docker-compose1.yml ps

**docker-compose pull**  拉取服务里定义的镜像



**docker-compose ps** 列出project所有运行容器



**docker-compose build** 构建/重新构建所有镜像

​		当某个service的Dockerfile改变时，即镜像发生改变需要重新生成时，如果仅仅是docker-compose.yml改变，只需要up重新启动project即可



**docker-compose start [serviceName]** 启动已存在但停止的所有service



**docker-compose up -d** 构建并启动整个project的所有service，相当于build+start，-d表示后台进程



**docker-compose stop [serviceName]** 停止运行的service



**docker-compose rm -f [serviceName]** 删除已停止的所有service，-f 强制删除



**docker-compose down -v** 停止并移除整个project的所有services， -v删除挂载卷和volume的链接



**docker-compose logs [serviceName]** 查看服务内所有容器日志输出



**docker-compose run service command**  在某个服务上运行命令



**docker-compose exec [serviceName] sh** 进入到某个容器



**docker-compose restart [serviceName]**  重启服务



**docker-compose config **验证和查看compose文件



**docker-compose images **列出所用的镜像



**docker-cpmpose scale ** 设置服务个数 Eg：docker-compose scale web=2 worker=3 



**docker-compose pause [serviceName]** 暂停服务



**docker-compose unpause [serviceName]** 恢复服务



> 常见流程：
>
> 启动：创建docker-compose.yaml  ->  docker-compose pull  -> docker-compose up -d
>
> 更新：docker-compose down -v -> docker-compose pull -> docker-compose up -d 



## docker 内跑显示图像的程序

在docker里面跑显示图像的程序,报错如下:

> (OpenPose 1.5.1:8456): Gtk-WARNING **: cannot open display: unix:0

**解决方法:**

在主机端查看环境变量中的DISPLAY

```sh
env | grep DISPLAY
# 显示结果如下
DISPLAY=:0
```

在主机端授予其它用户访问当前屏幕的权限,执行如下命令

```sh 
xhost +
```

 然后在docker容器中

```sh
export DISPLAY=:0
```

即可完美解决

