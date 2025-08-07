import { useEffect, useState } from 'react'
import { createOrder, createUser, listUsers } from './api'

export default function App() {
  const [users, setUsers] = useState<any[]>([])
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [productId, setProductId] = useState('')
  const [quantity, setQuantity] = useState(1)
  const [message, setMessage] = useState('')

  useEffect(() => {
    listUsers().then(setUsers).catch(() => setUsers([]))
  }, [])

  async function onCreateUser(e: React.FormEvent) {
    e.preventDefault()
    const user = await createUser(name, email)
    setUsers(u => [...u, user])
    setName(''); setEmail('')
    setMessage('User created')
  }

  async function onCreateOrder(e: React.FormEvent) {
    e.preventDefault()
    await createOrder(productId, quantity)
    setMessage('Order submitted')
  }

  return (
    <div style={{ fontFamily: 'system-ui, sans-serif', margin: 24 }}>
      <h2>Microservices Demo UI</h2>
      <p style={{ color: '#0a0' }}>{message}</p>

      <section style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 24 }}>
        <div>
          <h3>Create User</h3>
          <form onSubmit={onCreateUser}>
            <div>
              <label>Name</label><br />
              <input required value={name} onChange={e => setName(e.target.value)} />
            </div>
            <div>
              <label>Email</label><br />
              <input type="email" required value={email} onChange={e => setEmail(e.target.value)} />
            </div>
            <button type="submit">Create</button>
          </form>
        </div>

        <div>
          <h3>Create Order</h3>
          <form onSubmit={onCreateOrder}>
            <div>
              <label>Product ID</label><br />
              <input required value={productId} onChange={e => setProductId(e.target.value)} />
            </div>
            <div>
              <label>Quantity</label><br />
              <input type="number" min={1} required value={quantity} onChange={e => setQuantity(parseInt(e.target.value || '1'))} />
            </div>
            <button type="submit">Submit</button>
          </form>
        </div>
      </section>

      <h3 style={{ marginTop: 32 }}>Users</h3>
      <ul>
        {users.map(u => (
          <li key={u.id}>{u.name} ({u.email})</li>
        ))}
      </ul>
    </div>
  )
}