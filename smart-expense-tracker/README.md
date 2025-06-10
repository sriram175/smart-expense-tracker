# 💰 Smart Expense Tracker

A full-stack, secure, and modern expense tracking REST API built with **Java Spring Boot**. Users can register, log in, and manage their daily expenses — with JWT authentication, filtering, pagination, and Swagger-based API documentation.

---

## 🚀 Features

- 🔐 **JWT Authentication** (Login & Register)
- 📦 **CRUD Operations** on Expenses
- 📅 **Date Range Filtering**
- 📊 **Pagination & Sorting**
- 📘 **Swagger UI** for interactive API testing
- ☁️ **Cloud-hosted PostgreSQL (NeonDB)**

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.x
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL (via Neon Cloud)
- **API Docs:** Swagger (springdoc-openapi)

---

## 📦 API Endpoints

| Method | Endpoint         | Description                   | Auth Required |
|--------|------------------|-------------------------------|----------------|
| POST   | `/auth/register` | Register a new user           | ❌ No          |
| POST   | `/auth/login`    | Log in and receive JWT token  | ❌ No          |
| GET    | `/api/expenses`  | Get expenses (paginated)      | ✅ Yes         |
| POST   | `/api/expenses`  | Create new expense            | ✅ Yes         |
| PUT    | `/api/expenses/{id}` | Update expense by ID     | ✅ Yes         |
| DELETE | `/api/expenses/{id}` | Delete expense by ID     | ✅ Yes         |

---

## 🔧 Run Locally

### 1. Clone the Repository

```bash
git clone https://github.com/sriram175/smart-expense-tracker.git
cd smart-expense-tracker
