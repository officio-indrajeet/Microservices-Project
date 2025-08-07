import axios from 'axios'

const API_BASE = import.meta.env.VITE_API_BASE ?? 'http://localhost:8080'

export const api = axios.create({ baseURL: API_BASE })

export async function listUsers() {
  const { data } = await api.get('/api/users')
  return data.data
}

export async function createUser(name: string, email: string) {
  const { data } = await api.post('/api/users', { name, email })
  return data.data
}

export async function createOrder(productId: string, quantity: number) {
  const { data } = await api.post('/api/orders', { productId, quantity })
  return data.data
}