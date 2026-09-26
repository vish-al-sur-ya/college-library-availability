import { useEffect, useState } from 'react';
import { BookOpen, Check, ChevronRight, CircleAlert, Library, LogIn, LogOut, Pencil, Plus, RotateCcw, Search, ShoppingBag, Trash2, X } from 'lucide-react';
import { createBook, deleteBook, getBooks, getOrders, issueBook, login, orderBook, returnBook, updateBook } from './api';

const emptyBook = { title: '', author: '', isbn: '', category: 'General', description: '', shelfNumber: '', totalCopies: 1 };

function StatusBadge({ book }) {
  const available = book.availableCopies > 0;
  return <span className={`status ${available ? 'available' : 'issued'}`}><span />{available ? 'Available' : 'All issued'}</span>;
}

function BookForm({ book, onSave, onCancel }) {
  const [form, setForm] = useState(book || emptyBook);
  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });
  const submit = (event) => {
    event.preventDefault();
    onSave({ ...form, totalCopies: Number(form.totalCopies) });
  };
  return <form className="book-form" onSubmit={submit}>
    <div className="form-heading"><div><span className="eyebrow">Library records</span><h2>{book ? 'Edit book' : 'Add a book'}</h2></div><button type="button" className="icon-button" onClick={onCancel} aria-label="Close"><X size={18} /></button></div>
    <label>Title<input name="title" value={form.title} onChange={change} required /></label>
    <label>Author<input name="author" value={form.author} onChange={change} required /></label>
    <div className="two-fields"><label>ISBN<input name="isbn" value={form.isbn} onChange={change} required /></label><label>Category<input name="category" value={form.category} onChange={change} /></label></div>
    <div className="two-fields"><label>Shelf / rack<input name="shelfNumber" value={form.shelfNumber} onChange={change} /></label><label>Total copies<input name="totalCopies" type="number" min="1" value={form.totalCopies} onChange={change} required /></label></div>
    <label>Description<textarea name="description" rows="3" value={form.description} onChange={change} /></label>
    <div className="form-actions"><button type="button" className="button secondary" onClick={onCancel}>Cancel</button><button className="button primary" type="submit">{book ? 'Save changes' : 'Add book'}</button></div>
  </form>;
}

function LoginScreen({ onLogin, error }) {
  const [details, setDetails] = useState({ username: 'student', password: 'student123' });
  const submit = (event) => { event.preventDefault(); onLogin(details); };
  return <div className="login-page"><div className="login-card"><span className="brand-mark"><Library size={22} /></span><span className="eyebrow">College Library</span><h1>Welcome back.</h1><p>Sign in to check books, place orders, or manage the catalogue.</p><form onSubmit={submit}><label>Username<input value={details.username} onChange={(event) => setDetails({ ...details, username: event.target.value })} /></label><label>Password<input type="password" value={details.password} onChange={(event) => setDetails({ ...details, password: event.target.value })} /></label>{error && <div className="alert"><CircleAlert size={16} />{error}</div>}<button className="button primary" type="submit"><LogIn size={17} />Sign in</button></form><small>Demo student: student / student123<br />Demo admin: admin / admin123</small></div></div>;
}

function App() {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('libraryUser') || 'null'));
  const [books, setBooks] = useState([]);
  const [orders, setOrders] = useState([]);
  const [search, setSearch] = useState('');
  const [view, setView] = useState('catalogue');
  const [selected, setSelected] = useState(null);
  const [editing, setEditing] = useState(null);
  const [notice, setNotice] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (details) => { try { const loggedIn = await login(details); localStorage.setItem('libraryUser', JSON.stringify(loggedIn)); setUser(loggedIn); setError(''); } catch (err) { setError('Invalid username or password.'); } };

  const refresh = async (term = search) => {
    try { setBooks(await getBooks(term)); setError(''); } catch (err) { setError('Start the Spring Boot server to load the library.'); }
  };
  useEffect(() => { refresh(''); }, []);
  useEffect(() => { if (user) getOrders(user.username).then(setOrders).catch(() => setOrders([])); }, [user, notice]);
  const action = async (work, message) => { try { await work(); await refresh(); setNotice(message); setSelected(null); setEditing(null); setTimeout(() => setNotice(''), 2500); } catch (err) { setError(err.message.includes('No copies') ? err.message : 'The action could not be completed.'); } };
  const submitBook = (book) => action(() => editing?.id ? updateBook(editing.id, book) : createBook(book), editing ? 'Book updated.' : 'Book added.');
  const orderSelectedBook = () => action(() => orderBook(selected.id, user.username), 'Book ordered successfully.');
  const stats = { total: books.reduce((sum, book) => sum + book.totalCopies, 0), available: books.reduce((sum, book) => sum + book.availableCopies, 0) };

  if (!user) return <LoginScreen onLogin={handleLogin} error={error} />;

  return <div className="app-shell">
    <header className="topbar"><a className="brand" href="#top"><span className="brand-mark"><Library size={20} /></span><span>College Library<small>Availability Check</small></span></a><nav><button className={view === 'catalogue' ? 'active' : ''} onClick={() => setView('catalogue')}>Books</button><button className={view === 'orders' ? 'active' : ''} onClick={() => setView('orders')}>My orders</button>{user.role === 'ADMIN' && <button className={view === 'admin' ? 'active' : ''} onClick={() => setView('admin')}>Admin desk</button>}</nav><div className="user-menu">{user.username}<button onClick={() => { localStorage.removeItem('libraryUser'); setUser(null); }} title="Sign out"><LogOut size={16} /></button></div></header>
    <main id="top">
      {view === 'catalogue' ? <>
        <section className="hero"><div className="hero-copy"><span className="eyebrow">Campus reading room</span><h1>Find your next<br /><em>good read.</em></h1><p>Check availability before you walk to the library. Search by title, author, or ISBN.</p><div className="search-box"><Search size={20} /><input value={search} onChange={(event) => { setSearch(event.target.value); refresh(event.target.value); }} placeholder="Search the catalogue..." /><kbd>⌘ K</kbd></div></div><img src="/library-illustration.svg" alt="Illustration of the college library" /></section>
        <section className="content"><div className="section-heading"><div><span className="eyebrow">Library catalogue</span><h2>Browse all books</h2></div><div className="quick-stats"><span><strong>{books.length}</strong> titles</span><span><strong>{stats.available}</strong> copies available</span></div></div>
          {error && <div className="alert"><CircleAlert size={18} />{error}</div>}
          <div className="book-grid">{books.map(book => <article className="book-card" key={book.id} onClick={() => setSelected(book)}><div className="cover-wrap"><img src="/book-cover.svg" alt="" /><span className="category">{book.category}</span></div><div className="book-info"><StatusBadge book={book} /><h3>{book.title}</h3><p>by {book.author}</p><div className="book-meta"><span>{book.availableCopies} of {book.totalCopies} available</span><span className="detail-link">Details <ChevronRight size={15} /></span></div></div></article>)}</div>
          {!books.length && !error && <div className="empty"><BookOpen size={30} /><p>No books match that search.</p></div>}
        </section>
      </> : view === 'orders' ? <section className="content orders-view"><div className="admin-heading"><div><span className="eyebrow">Student account</span><h1>My orders</h1><p>Books you have requested from the library.</p></div><ShoppingBag size={42} /></div>{orders.length ? <div className="order-list">{orders.map(order => <div className="order-item" key={order.id}><div className="order-icon"><BookOpen size={20} /></div><div><strong>{order.bookTitle}</strong><small>Ordered on {order.orderedAt.replace('T', ' ')}</small></div><span className="status available"><span />{order.status}</span></div>)}</div> : <div className="empty"><ShoppingBag size={30} /><p>You have not ordered any books yet.</p></div>}</section> : <section className="admin-view content"><div className="admin-heading"><div><span className="eyebrow">Librarian workspace</span><h1>Admin desk</h1><p>Keep the catalogue accurate and up to date.</p></div><button className="button primary" onClick={() => setEditing({ ...emptyBook })}><Plus size={17} />Add book</button></div>{notice && <div className="toast"><Check size={17} />{notice}</div>}{error && <div className="alert"><CircleAlert size={18} />{error}</div>}<div className="admin-table-wrap"><table><thead><tr><th>Book</th><th>ISBN</th><th>Location</th><th>Copies</th><th>Status</th><th>Actions</th></tr></thead><tbody>{books.map(book => <tr key={book.id}><td><strong>{book.title}</strong><small>{book.author}</small></td><td>{book.isbn}</td><td>{book.shelfNumber || 'Unassigned'}</td><td>{book.availableCopies} / {book.totalCopies}</td><td><StatusBadge book={book} /></td><td><div className="row-actions"><button title="Issue book" disabled={!book.availableCopies} onClick={() => action(() => issueBook(book.id), 'Book issued.')}><BookOpen size={16} /></button><button title="Return book" disabled={book.issuedCopies === 0} onClick={() => action(() => returnBook(book.id), 'Book returned.')}><RotateCcw size={16} /></button><button title="Edit book" onClick={() => setEditing(book)}><Pencil size={16} /></button><button title="Delete book" onClick={() => { if (window.confirm(`Delete ${book.title}?`)) action(() => deleteBook(book.id), 'Book deleted.'); }}><Trash2 size={16} /></button></div></td></tr>)}</tbody></table></div></section>}
    </main>
    {selected && <div className="modal-backdrop" onClick={() => setSelected(null)}><div className="modal detail-modal" onClick={(event) => event.stopPropagation()}><button className="icon-button close" onClick={() => setSelected(null)} aria-label="Close"><X size={18} /></button><img src="/book-cover.svg" alt="" /><div><StatusBadge book={selected} /><span className="eyebrow">{selected.category}</span><h2>{selected.title}</h2><p className="author">{selected.author}</p><p>{selected.description || 'A useful addition to the college library collection.'}</p><div className="detail-list"><span><b>ISBN</b>{selected.isbn}</span><span><b>Shelf / rack</b>{selected.shelfNumber || 'Unassigned'}</span><span><b>Availability</b>{selected.availableCopies} of {selected.totalCopies} copies</span></div><button className="button primary order-button" disabled={!selected.availableCopies} onClick={orderSelectedBook}><ShoppingBag size={17} />{selected.availableCopies ? 'Order this book' : 'Currently unavailable'}</button></div></div></div>}
    {editing && <div className="modal-backdrop"><div className="modal"><BookForm book={editing.id ? editing : null} onSave={submitBook} onCancel={() => setEditing(null)} /></div></div>}
  </div>;
}

export default App;
