import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 20, // usuários virtuais
    duration: '30s', // duração do teste
};

export default function () {
    const url = 'http://localhost:8080/produto'; // substitua pela URL real da API

    const precoAleatorio = Math.floor(Math.random() * 100) + 1;

    const payload = JSON.stringify({
        nome: 'string',
        descricao: 'string',
        preco: precoAleatorio,
        categoria: 'lanche'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, payload, params);

    check(res, {
        'status é 200 ou 201': (r) => r.status === 200 || r.status === 201,
    });
}