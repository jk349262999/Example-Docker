# Example-Docker
### Docker重要命令

#### 镜像相关

- 搜索镜像
     > **`docker search`**
     > `docker search nginx`
 
 - 下载镜像
     >  **`docker pull`**
     > `docker pull nginx` 或者 `docker pull reg.jianzh5.com/nginx:1.7.9`
 
 - 列出镜像
     > **`docker images`**
     > `docker images` 或者 `docker images hello-world`
 
 - 删除镜像
     > **`docker rmi`**
     > `docker rmi hello-world`
 
 - 构建镜像
     > **`docker build`**

#### 容器相关

- 新建启动镜像
>**`docker run`**
```
① -d选项：表示后台运行
② -P选项（大写）：随机端口映射
③ -p选项（小写）：指定端口映射,前面是宿主机端口后面是容器端口，如 docker run nginx -p 8080:80，将容器的80端口映射到宿主机的8080端口，然后使用 localhost:8080就可以查看容器中nginx的欢迎页了
④ -v选项：挂载宿主机目录，前面是宿主机目录，后面是容器目录,如 docker run -d -p 80:80 -v /dockerData/nginx/conf/nginx.conf:/etc/nginx/nginx.conf nginx 挂载宿主机的 /dockerData/nginx/conf/nginx.conf的文件，这样就可以在宿主机对 nginx进行参数配置了,注意目录需要用绝对路径，不要使用相对路径，如果宿主机目录不存在则会自动创建。
⑤--rm : 停止容器后会直接删除容器，这个参数在测试是很有用，如 docker run -d -p 80:80 --rm nginx
⑥--name : 给容器起个名字，否则会出现一长串的自定义名称如 docker run -name niginx -d -p 80:80 - nginx
```

- 列出容器
    > **`docker ps`**
    >`docker ps` 或者 `docker ps -a`

- 停止容器
    > **`docker stop`**
    > `docker stop 5d034c6ea010`  或者 `docker stop hello-world`

- 启动停止的容器
    > **`docker start`**
    > `docker start 5d034c6ea010`  或者 `docker start hello-world`

- 重启容器
    > **`docker restart`**
    > `docker restart 5d034c6ea010`  或者 `docker restart hello-world`

- 进入容器
    > **`docker exec -it 容器id /bin/bash`**
    > `docker exec -it 5d034c6ea010 /bin/bash`  

- 删除容器
    > **`docker rm`**
    > `docker rm 5d034c6ea010`  或者 `docker rm hello-world`

- 数据拷贝
    > **`docker cp`** 
    > `docker cp 5d034c6ea010: /etc/nginx/nginx.conf /dockerData/nginx/conf/nginx.conf`

- 查看日志
    > **`docker logs`**
    > `docker logs -f -t --since="2017-05-31" --tail=10 edu_web_1`
    > -f : 查看实时日志
    > -t : 查看日志产生的日期
    > -tail=10 : 查看最后的10条日志。
    > edu_web_1 : 容器名称

#### 运行测试
```
docker run -d -p 10080:80 --name nginx -rm nginx 
```
```
docker cp nginx:/etc/nginx/nginx.conf D:\Repository\Docker\FileSharing\nginx\conf\nginx.conf 
```
```
docker cp nginx:/etc/nginx/conf.d D:\Repository\Docker\FileSharing\nginx/conf/conf.d
```
```
docker cp nginx:/usr/share/nginx/html D:\Repository\Docker\FileSharing\nginx\www
```
```
docker cp nginx:/var/log/nginx D:\Repository\Docker\FileSharing\nginx\logs 
```
```
docker stop nginx
```
```
docker run -d -p 10080:80 --name nginx -v D:/Repository/Docker/FileSharing/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v D:/Repository/Docker/FileSharing/nginx/conf/conf.d:/etc/nginx/conf.d -v D:/Repository/Docker/FileSharing/nginx/www:/usr/share/nginx/html -v D:/Repository/Docker/FileSharing/nginx/logs:/var/log/nginx nginx
```

### Dockerfile

#### 指令详解

- FROM
    > 选择一个基础镜像，然后在基础镜像上进行修改，比如构建一个SpringBoot项目的镜像，就需要选择java这个基础镜像，FROM需要作为Dockerfile中的第一条指令
    > 如：`FROM openjdk:8-jdk-alpine` 基础镜像如果可以的话最好使用alpine版本的，采用alpline版本的基础镜像构建出来的镜像会小很多。

- RUN
    > RUN指令用来执行命令行命令的。它有以下两种格式：
    - shell 格式：RUN <命令>，就像直接在命令行中输入的命令一样。`RUN echo '<h1>Hello, Docker!</h1>' >`
    - exec 格式：RUN ["可执行文件", "参数1", "参数2"]，这更像是函数调用中的格式。

- CMD
    > 此指令就是用于指定默认的容器主进程的启动命令的。它有以下两种格式：
    - shell 格式：CMD <命令>
    - exec 格式：CMD ["可执行文件", "参数1", "参数2"...]
    - 参数列表格式：CMD ["参数1", "参数2"...]。在指定了 ENTRYPOINT 指令后，用 CMD 指定具体的参数。

- ENTRYPOINT
    > 格式和 RUN指令格式一样，分为 exec 格式和 shell 格式。 ENTRYPOINT 的目的和 CMD 一样，都是在指定容器启动程序及参数。ENTRYPOINT 在运行时也可以替代，不过比 CMD 要略显繁琐，需要通过 docker run 的参数 --entrypoint 来指定。
    > 当指定了 ENTRYPOINT 后，CMD 的含义就发生了改变，不再是直接的运行其命令，而是将 CMD 的内容作为参数传给 ENTRYPOINT指令，换句话说实际执行时，将变为：
`ETRYPOINT ["java","-jar","/app.jar"]`
    
- COPY & ADD
    > 这2个指令都是复制文件，它将从构建上下文目录中 <源路径> 的文件/目录 复制到新的一层的镜像内的 <目标路径> 位置。比如：COPY demo-test.jar app.jar 或 ADD demo-test.jar app.jar。
    > ADD指令比 COPY高级点，可以指定一个URL地址，这样Docker引擎会去下载这个URL的文件，如果 ADD后面是一个 tar文件的话，Dokcer引擎还会去解压缩。
    **我们在构建镜像时尽可能使用 COPY，因为 COPY 的语义很明确，就是复制文件而已，而 ADD 则包含了更复杂的功能，其行为也不一定很清晰。**

- EXPOSE
    > 声明容器运行时的端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务。在 Dockerfile 中写入这样的声明有两个好处，一个是帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射；另一个用处则是在运行时使用随机端口映射时，也就是 docker run -P 时，会自动随机映射 EXPOSE 的端口。
    > 要将 EXPOSE 和在运行时使用 -p <宿主端口>:<容器端口> 区分开来。-p，是映射宿主端口和容器端口，换句话说，就是将容器的对应端口服务公开给外界访问，而 EXPOSE 仅仅是声明容器打算使用什么端口而已，并不会自动在宿主进行端口映射。
- ENV
    > 这个指令很简单，就是设置环境变量，无论是后面的其它指令，如 RUN，还是运行时的应用，都可以直接使用这里定义的环境变量。它有如下两种格式：
    - `NV <key> <value>`
    - `ENV <key1>=<value1> <key2>=<value2>...`
- VOLUME
    > 该指令使容器中的一个目录具有持久化存储的功能，该目录可被容器本身使用，也可共享给其他容器。当容器中的应用有持久化数据的需求时可以在Dockerfile中使用该指令。如 VOLUME /tmp
    > 这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层，从而保证了容器存储层的无状态化。当然，运行时可以覆盖这个挂载设置。比如：`docker run -d -v mydata:/tmp xxxx`
- LABEL
    > 你可以为你的镜像添加labels，用来组织镜像，记录版本描述，或者其他原因，对应每个label，增加以LABEL开头的行，和一个或者多个键值对。如下所示：
    > `LABEL version="1.0"`
    > `LABEL description="test"`

#### 运行测试
