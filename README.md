# BlogDDD - 基于领域驱动设计的博客系统

[![Java Version](https://img.shields.io/badge/Java-17-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.1-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
一个遵循领域驱动设计（DDD）原则构建的博客系统核心框架，提供文章管理、分类体系和标签系统的标准化实现。

## 🚀 已实现功能

### 📚 核心功能
- **文章管理**：创建/编辑/发布文章（支持Markdown格式）
- **分类体系**：多级分类管理（支持排序和置顶）
- **标签系统**：灵活的标签关联机制
- **热门文章**：基于发布时间和互动的热度计算

### 🏗️ 架构特性
- **清晰分层架构**：接口层/应用层/领域层/基础设施层
- **领域模型**：
    - 文章聚合根（Article Aggregate）
    - 分类实体（Category Entity）
    - 标签实体（Tag Entity）
- **CQRS实践**：查询与命令分离设计
- **Swagger集成**：完整的API文档支持

### 🛠️ 技术实现
- JPA规范仓储实现
- 自动化的DTO映射（MapStruct）
- 事务边界控制（@Transactional）
- 全局异常处理
- Lombok简化代码

## 📂 模块结构
~~~ bash
blog-service/ 
├── blog-interfaces # 接口层 
│ └── rest/ # REST API控制器 
├── blog-application # 应用层 
│ ├── dto/ # 数据传输对象 
│ └── service/ # 应用服务实现 
├── blog-domain # 领域层核心 
│ ├── article/ # 文章上下文 
│ │ ├── model/ # 聚合根/值对象 
│ │ └── service/ # 领域服务 
├── blog-infrastructure # 基础设施 
│ ├── dataaccess/ # 持久化实现 
│ └── config/ # 配置类
~~~
## 🧩 技术栈

**核心框架**  
![Spring Boot](https://img.shields.io/badge/-Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/-JPA-59666C?logo=hibernate&logoColor=white)

**数据库**  
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-4169E1?logo=postgresql&logoColor=white)

**开发工具**  
![Lombok](https://img.shields.io/badge/-Lombok-pink)
![MapStruct](https://img.shields.io/badge/-MapStruct-orange)

**API文档**  
![Swagger](https://img.shields.io/badge/-Swagger-85EA2D?logo=swagger&logoColor=black)

## 🚀 快速开始

### 环境要求
- Java 17+
- PostgreSQL 15+
- Maven 3.8+

### 初始化步骤
1. 创建数据库
~~~ sql
CREATE DATABASE blogdb;
~~~
2. 克隆仓库
~~~ bash
git clone https://github.com/yourusername/blog-ddd.git
~~~
3. 修改配置
~~~ yaml
application.yml
spring: 
    datasource: 
    url: jdbc:postgresql://localhost:5432/blogdb 
    username: postgres 
    password: yourpassword
~~~
4. 启动应用
~~~ bash
mvn spring-boot:run -pl blog-container
~~~
访问API文档：  
🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧬 领域模型
~~~ mermaid
classDiagram
class Article 
{ 
<<Aggregate Root>> 
-UUID id 
-String title 
-String content 
-Category category 
-List<Tag> tags 
-LocalDateTime publishTime 
+publish() 
+updateContent() 
}
class Category {
    <<Entity>>
    -Long id
    -String name
    -Integer sort
}

class Tag {
    <<Entity>>
    -Long id
    -String name
}

Article "1" *-- "1" Category
Article "1" *-- "n" Tag
~~~
## 📅 开发计划

### 近期规划
- [ ] 用户认证模块（JWT实现）
- [ ] 评论系统集成
- [ ] 全文搜索功能（Elasticsearch）
- [ ] 文件上传服务（OSS集成）

### 架构优化
- [ ] 事件驱动架构改造
- [ ] CQRS模式深度实现
- [ ] 缓存策略优化（Redis集成）

### 运维增强
- [ ] Docker容器化部署
- [ ] Prometheus监控集成
- [ ] CI/CD流水线搭建

## 🤝 参与贡献
欢迎通过Issue提交建议或通过Pull Request贡献代码（请遵循[贡献指南](./CONTRIBUTING.md)）
## 📜 许可证
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) © 2025 Jackmouse
