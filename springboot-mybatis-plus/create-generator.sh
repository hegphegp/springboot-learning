#!/bin/bash

# 运行该脚本，直接下载并提取mybatis-plus-generator模块作为单独的项目
git clone https://gitee.com/baomidou/mybatis-plus.git
cd mybatis-plus
project_latest_version=$(gradle properties | grep version | sed 's/version: //g')
rm -rf mybatis-plus-generator/build
rm -rf mybatis-plus-generator/out

rm -rf mybatis-plus-generator-new
mkdir -p mybatis-plus-generator-new
cd mybatis-plus-generator-new
cp -r ../mybatis-plus-generator/* .

# 可能只适用于3.0的版本，不知道其他版本的依赖是否变了
tee build.gradle  <<-'EOF'
buildscript {
    ext {
        springBootVersion = '1.5.12.RELEASE'
    }
    repositories {
        mavenLocal()
        maven{ url "http://maven.aliyun.com/nexus/content/groups/public/"}
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'

group = 'com.baomidou'
version = 'project_latest_version'
def artifactId = 'mybatis-plus-generator-new'
sourceCompatibility = 1.8

repositories {
    mavenLocal()
    maven{ url "http://maven.aliyun.com/nexus/content/groups/public/"}
    mavenCentral()
}

dependencies {
    compile('org.springframework.boot:spring-boot-starter')
    compile("com.baomidou:mybatis-plus-extension:project_latest_version")
//    compile("com.baomidou:mybatis-plus-generator:project_latest_version")
    compile('org.apache.velocity:velocity-engine-core:2.0')
    compile('org.freemarker:freemarker:2.3.9')
    compile('mysql:mysql-connector-java:5.1.46')
//    compile('com.microsoft.sqlserver:mssql-jdbc')
//    compile('org.postgresql:postgresql')
//    compile('com.oracle:ojdbc6')
    compile('com.alibaba:fastjson:1.2.39')
    testCompile('com.h2database:h2:1.4.197')
    compileOnly('org.projectlombok:lombok:1.18.0')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
EOF

sed -i "s/project_latest_version/$project_latest_version/g" build.gradle
