# SpringBoot+MongoDB入门操作

## 1、准备工作

### pom.xml

```
		<!-- 继承Spring boot -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.5.RELEASE</version>
</parent>
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
</dependencies>
```

### application.properties

```
server.port=8091

#数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

#mybatis配置
mybatis.type-aliases-package=com.congge.entity
mybatis.mapper-locations=classpath:mybatis/*.xml

#mongodb配置
spring.data.mongodb.uri=mongodb://192.168.111.132:27017/congge

```

### 创建文档

```java
package com.congge.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection="book")
public class Book {
	
	@Id
	private String id;
	private Integer price;
	private String name;
	private String info;
	private String publish;
	private Date createTime;
	private Date updateTime;

}
```

### 业务层操作

```java
@Service
public class MongoDbService {

	private static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 保存对象
	 * @param book
	 * @return
	 */
	public String saveObj(Book book) {
		book.setCreateTime(new Date());
		book.setUpdateTime(new Date());
		mongoTemplate.save(book);
		return "添加成功";
	}

	/**
	 * 查询所有
	 * @return
	 */
	public List<Book> findAll() {
		return mongoTemplate.findAll(Book.class);
	}

	/***
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Book getBookById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return mongoTemplate.findOne(query, Book.class);
	}

	/**
	 * 根据名称查询
	 *
	 * @param name
	 * @return
	 */
	public Book getBookByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, Book.class);
	}

	/**
	 * 更新对象
	 *
	 * @param book
	 * @return
	 */
	public String updateBook(Book book) {
		Query query = new Query(Criteria.where("_id").is(book.getId()));
		Update update = new Update().set("publish", book.getPublish()).set("info", book.getInfo()).set("updateTime",
				new Date());
		// updateFirst 更新查询返回结果集的第一条
		mongoTemplate.updateFirst(query, update, Book.class);
		// updateMulti 更新查询返回结果集的全部
		// mongoTemplate.updateMulti(query,update,Book.class);
		// upsert 更新对象不存在则去添加
		// mongoTemplate.upsert(query,update,Book.class);
		return "success";
	}

	/***
	 * 删除对象
	 * @param book
	 * @return
	 */
	public String deleteBook(Book book) {
		mongoTemplate.remove(book);
		return "success";
	}

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	public String deleteBookById(String id) {
		// findOne
		Book book = getBookById(id);
		// delete
		deleteBook(book);
		return "success";
	}
	
	/**
	 * 模糊查询
	 * @param search
	 * @return
	 */
	public List<Book> findByLikes(String search){
		Query query = new Query();
		Criteria criteria = new Criteria();
		//criteria.where("name").regex(search);
		Pattern pattern = Pattern.compile("^.*" + search + ".*$" , Pattern.CASE_INSENSITIVE);
		criteria.where("name").regex(pattern);
		List<Book> lists = mongoTemplate.findAllAndRemove(query, Book.class);
		return lists;
	}

}

```

### 控制层操作

```java
@RestController
public class MongoDbController {
	
	@Autowired
	private MongoDbService mongoDbService;

	@PostMapping("/mongo/save")
	public String saveObj(@RequestBody Book book) {
		return mongoDbService.saveObj(book);
	}

	@GetMapping("/mongo/findAll")
	public List<Book> findAll() {
		return mongoDbService.findAll();
	}

	@GetMapping("/mongo/findOne")
	public Book findOne(@RequestParam String id) {
		return mongoDbService.getBookById(id);
	}

	@GetMapping("/mongo/findOneByName")
	public Book findOneByName(@RequestParam String name) {
		return mongoDbService.getBookByName(name);
	}

	@PostMapping("/mongo/update")
	public String update(@RequestBody Book book) {
		return mongoDbService.updateBook(book);
	}

	@PostMapping("/mongo/delOne")
	public String delOne(@RequestBody Book book) {
		return mongoDbService.deleteBook(book);
	}

	@GetMapping("/mongo/delById")
	public String delById(@RequestParam String id) {
		return mongoDbService.deleteBookById(id);
	}
	
	@GetMapping("/mongo/findlikes")
	public List<Book> findByLikes(@RequestParam String search) {
		return mongoDbService.findByLikes(search);
	}
}
```

