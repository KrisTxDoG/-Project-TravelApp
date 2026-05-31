# Travel App Project

旅遊應用全棧項目，包含 Angular 前端和 Java Spring Boot 後端。

## 專案概述
- **前端**: Angular (TypeScript) - 水藍色漸層主題，響應式設計
- **後端**: Java Spring Boot - RESTful API
- **主要功能**: 用戶登錄、記錄旅遊天數、編輯行程

## 項目結構
```
Project_TravelApp/
├── travel-frontend/          # Angular 前端應用
│   ├── src/
│   ├── angular.json
│   └── package.json
├── travel-backend/           # Java 後端應用
│   ├── src/
│   ├── pom.xml
│   └── mvn files
└── docker-compose.yml        # 容器編排
```

## 開發進度

- [ ] 創建 Angular 前端項目
- [ ] 創建 Java Spring Boot 後端
- [ ] 配置數據庫 (MySQL)
- [ ] 實現認證系統
- [ ] 實現旅程管理功能
- [ ] 集成前後端
- [ ] 測試和部署

## 快速開始

### 前端開發
```bash
cd travel-frontend
npm install
ng serve
```

### 後端開發
```bash
cd travel-backend
mvn clean install
mvn spring-boot:run
```

## 技術棧

**前端:**
- Angular 17+
- TypeScript
- Tailwind CSS (水藍色主題)
- RxJS

**後端:**
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- MySQL 8.0

