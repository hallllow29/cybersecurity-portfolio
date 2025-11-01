# 🍽️ Restaurante API

API backend para um sistema de gestão de restaurantes e encomendas, construída com **Node.js**, **Express** e **MongoDB**.

---

## 📦 Tecnologias

- Node.js + Express
- MongoDB + Mongoose
- JWT Authentication
- Multer (upload de imagens)
- Socket.IO (tempo real)
- Angular (frontend externo)

---

## 🚀 Endpoints

### 🔐 Autenticação (`/api/auth`)
| Método | Endpoint           | Descrição               |
|--------|--------------------|-------------------------|
| POST   | `/auth/login`      | Login de utilizador     |
| POST   | `/auth/logout`     | Logout do utilizador    |
| POST   | `/auth/registar`   | Registo de cliente ou restaurante |

---

### 👥 Utilizador (`/api/utilizador`)
| Método | Endpoint                       | Descrição                  |
|--------|--------------------------------|----------------------------|
| GET    | `/utilizador/dashboard`        | Dashboard do cliente       |
| GET    | `/utilizador/restaurantes/:id/menu` | Ver menu de um restaurante |
| GET    | `/utilizador/carrinho`         | Ver carrinho               |
| POST   | `/utilizador/carrinho/adicionar` | Adicionar prato ao carrinho |
| POST   | `/utilizador/encomendar`       | Criar encomenda            |
| GET    | `/utilizador/encomendas`       | Ver encomendas do cliente  |
| POST   | `/utilizador/encomendas/:id/cancelar` | Cancelar encomenda        |

---

### 🍽️ Restaurante (`/api/restaurante`)
| Método | Endpoint                                  | Descrição                        |
|--------|-------------------------------------------|----------------------------------|
| GET    | `/restaurante/dashboard/:id`              | Ver dashboard do restaurante     |
| PUT    | `/restaurante/perfil`                     | Editar perfil                    |
| GET    | `/restaurante/perfil`                     | Ver perfil do restaurante        |
| GET    | `/restaurante/dashboard/encomendas`       | Listar encomendas                |
| GET    | `/restaurante/dashboard/encomendas/:id`   | Ver detalhes da encomenda        |
| POST   | `/restaurante/dashboard/encomendas/:id/status` | Atualizar estado da encomenda |

---

### 📋 Menus Públicos (`/api/menus`)
| Método | Endpoint             | Descrição                     |
|--------|----------------------|-------------------------------|
| GET    | `/menus?restaurante=ID` | Listar menus de um restaurante |
| GET    | `/menus/:id`         | Obter detalhes de um menu     |

---

### 🧑‍🍳 Menus Privados (`/api/admin/menus`)
| Método | Endpoint           | Descrição                |
|--------|--------------------|--------------------------|
| POST   | `/admin/menus`     | Criar novo menu          |
| PUT    | `/admin/menus/:id` | Editar menu              |
| DELETE | `/admin/menus/:id` | Apagar menu              |

---

### 🍛 Pratos Públicos (`/api/pratos`)
| Método | Endpoint                    | Descrição                  |
|--------|-----------------------------|----------------------------|
| GET    | `/pratos?restaurante=ID`    | Listar pratos públicos     |
| GET    | `/pratos/:id`               | Detalhes de um prato       |

---

### 🍽️ Pratos Privados (`/api/admin/pratos`)
| Método | Endpoint                         | Descrição                |
|--------|----------------------------------|--------------------------|
| GET    | `/admin/pratos`                  | Listar pratos do restaurante |
| GET    | `/admin/pratos/:id`              | Ver prato para editar     |
| POST   | `/admin/pratos`                  | Criar novo prato          |
| PUT    | `/admin/pratos/:id`              | Editar prato              |
| DELETE | `/admin/pratos/:id`              | Apagar prato              |
| POST   | `/admin/pratos/apagar-selecionados` | Apagar múltiplos pratos |

---

### 🛡️ Admin (`/api/admin`)
| Método | Endpoint                              | Descrição                   |
|--------|----------------------------------------|-----------------------------|
| GET    | `/admin/dashboard`                    | Ver dashboard admin         |
| POST   | `/admin/restaurante/:id/validar`      | Validar restaurante         |
| POST   | `/admin/restaurante/:id/remover`      | Apagar restaurante          |
| POST   | `/admin/utilizador/:id/remover`       | Apagar utilizador           |

---

## 🗄 Estrutura de projeto (back-end)

