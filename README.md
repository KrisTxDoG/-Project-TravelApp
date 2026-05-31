# Travel Planner App

一個完整的全棧旅遊規劃應用，前端使用 Angular，後端使用 Java Spring Boot。

## 功能特點

✨ **核心功能**
- 👤 用戶註冊和登錄
- 📅 建立和管理旅遊計畫
- 🎯 記錄旅遊天數
- 📍 編輯行程細節
- 📱 完全響應式設計

🎨 **UI/UX**
- 水藍色漸層主題
- 移動設備優化
- 平滑的動畫和轉換
- 直觀的用戶界面

## 技術棧

### 前端
- **框架**: Angular 17+
- **語言**: TypeScript
- **樣式**: Tailwind CSS
- **HTTP**: RxJS & HttpClient

### 後端
- **框架**: Spring Boot 3.x
- **語言**: Java 17+
- **數據庫**: MySQL 8.0
- **認證**: JWT (JSON Web Tokens)
- **ORM**: Spring Data JPA

## 項目結構

```
Project_TravelApp/
├── travel-frontend/          # Angular 前端應用
│   ├── src/
│   │   ├── app/
│   │   │   ├── pages/        # 頁面組件
│   │   │   ├── services/     # HTTP 服務
│   │   │   └── app.routes.ts # 路由配置
│   │   └── main.ts
│   ├── angular.json
│   └── package.json
│
├── travel-backend/           # Java Spring Boot 後端
│   ├── src/main/java/com/travelapp/
│   │   ├── controller/       # REST 控制器
│   │   ├── entity/           # JPA 實體
│   │   ├── service/          # 業務邏輯
│   │   ├── repository/       # 數據訪問層
│   │   └── security/         # 安全配置
│   ├── pom.xml
│   └── src/main/resources/
│
└── docker-compose.yml        # 容器編排
```

## 快速開始

### 前提條件
- Node.js 18+
- Java 17+
- MySQL 8.0
- Maven

### 步驟 1: 設置數據庫

```bash
# 創建數據庫
mysql -u root -p
CREATE DATABASE travel_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

### 步驟 2: 運行後端

```bash
cd travel-backend

# 安裝依賴
mvn clean install

# 運行應用
mvn spring-boot:run
```

後端將在 `http://localhost:8080` 上運行

### 步驟 3: 運行前端

```bash
cd travel-frontend

# 安裝依賴
npm install

# 啟動開發服務器
npm start
```

前端將在 `http://localhost:4200` 上運行

## API 端點

### 認證
- `POST /api/auth/login` - 用戶登錄
- `POST /api/auth/register` - 用戶註冊

### 旅遊計畫
- `GET /api/travel/plans` - 獲取所有計畫
- `POST /api/travel/plans` - 建立新計畫
- `GET /api/travel/plans/{id}` - 獲取特定計畫
- `DELETE /api/travel/plans/{id}` - 刪除計畫

### 行程
- `POST /api/travel/plans/{planId}/itineraries` - 新增行程
- `DELETE /api/travel/plans/{planId}/itineraries/{itineraryId}` - 刪除行程

## 使用 Docker

```bash
# 使用 docker-compose 啟動所有服務
docker-compose up -d

# 停止服務
docker-compose down
```

## 開發指南

### 前端開發

1. 修改組件文件在 `src/app/pages/`
2. 修改服務在 `src/app/services/`
3. 修改路由在 `src/app/app.routes.ts`
4. 修改樣式在相應的 `.css` 文件

### 後端開發

1. 修改實體在 `src/main/java/com/travelapp/entity/`
2. 修改控制器在 `src/main/java/com/travelapp/controller/`
3. 修改服務在 `src/main/java/com/travelapp/service/`
4. 修改配置在 `application.properties`

## 常見問題

### 連接數據庫出錯
- 確保 MySQL 正在運行
- 檢查 `application.properties` 中的數據庫 URL
- 確保数据库已創建

### CORS 錯誤
- 檢查後端的 CORS 配置
- 確保前端 URL 在允許列表中

### JWT 令牌過期
- 令牌有效期為 24 小時
- 需要重新登錄以獲取新令牌

## 部署

### 前端部署
```bash
npm run build
# 構建的文件在 dist/ 目錄中，可部署到任何靜態主機
```

### 後端部署
```bash
mvn clean package
# JAR 文件在 target/ 目錄中
java -jar target/travel-backend-1.0.0.jar
```

## 貢獻

歡迎提交 Pull Request 和 Issue！

## 許可

MIT 許可

## 聯繫方式

如有問題，請聯繫項目維護者。
