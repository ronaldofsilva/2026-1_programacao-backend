// src/middlewares/auth.js
// Esta implementação é conceitual, por não fizemos autenticação no banco de dados
// Não temos coleções para usuários
// Observe a lógica e o fluxo operacional
import jwt from "jsonwebtoken";

const SECRET = process.env.JWT_SECRET || "chave-secreta-super-segura";

export function authenticateToken(req, res, next) {
  // Extrair token do header Authorization
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1]; // "Bearer TOKEN"
 
  if (!token) {
    return res.status(401).json({ 
      message: "Token não fornecido" 
    });
  }
  // Verificar e decodificar token
  jwt.verify(token, SECRET, (err, user) => {
    if (err) {
      return res.status(403).json({ 
        message: "Token inválido ou expirado" 
      });
    }
    // Adicionar dados do usuário na requisição
    req.user = user;  // { userId, name, ... }
    next();
  });
}

// Middleware para verificar se é o dono da foto

export function checkPhotoOwnership(req, res, next) {
  const { peopleId } = req.body;
  const authenticatedUserId = req.user.userId;
  
  // Só pode fazer upload de foto própria
  if (personId !== authenticatedUserId) {
    return res.status(403).json({ 
      message: "Você só pode fazer upload de suas próprias fotos" 
    });
  }
  
  next();
}
