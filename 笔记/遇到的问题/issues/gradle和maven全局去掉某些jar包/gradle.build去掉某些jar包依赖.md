# gradle.build去掉某些jar包依赖

#### 查看整个项目的jar包依赖
```
gradle dependencies
```

##### 方法一(在具体的某个dependency中排除)
```groovy
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web') {
        exclude group: "org.slf4j", module: "slf4j-log4j12"
    }
}
```

##### 方法二(在具体的某个dependency中排除)
```groovy
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web') {
        exclude group: "org.slf4j"
    }
}
```

##### 方法三(在具体的某个dependency中排除)
```groovy
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web') {
        exclude module: "slf4j-log4j12"
    }
}
```

##### 方法四(直接在configuration中排除)
```groovy
dependencies {
    compile('org.springframework.boot:spring-boot-starter')
    configurations {
        all*.exclude group: "org.slf4j", module: "slf4j-log4j12"
    }
}
```

##### 方法五(直接在configuration中排除)
```groovy
dependencies {
    compile('org.springframework.boot:spring-boot-starter')
}

configurations {
    all*.exclude group: "org.slf4j", module: "slf4j-log4j12"
}
```
