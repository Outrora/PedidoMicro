
db = db.getSiblingDB('admin');
try {
    db.createUser({
        user: process.env.MONGO_INITDB_ROOT_USERNAME,
        pwd: process.env.MONGO_INITDB_ROOT_PASSWORD,
        roles: [
            { role: "userAdminAnyDatabase", db: "admin" },
            { role: "readWriteAnyDatabase", db: "admin" }
        ]
    });
} catch (e) {
    print("Usuário admin já existe, ignorando...");
}
// Criar usuário root


db = db.getSiblingDB('pedidos'); // valor fixo

db.createCollection("pedidos");

try {
    // Criar usuário específico para o banco de pedidos
    db.createUser({
        user: process.env.MONGO_USER, // valor fixo
        pwd: process.env.MONGO_PASSWORD, // valor fixo
        roles: [
            { role: "dbOwner", db: "pedidos" },
            { role: "readWrite", db: "pedidos" }
        ]
    });
} catch (e) {
    print("Usuário paulo já existe, ignorando...");
}
