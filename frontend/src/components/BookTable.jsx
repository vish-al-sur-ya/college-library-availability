import { Link } from "react-router-dom";

const BookTable = ({ books, showActions = false, onEdit, onDelete }) => {
  if (books.length === 0) {
    return <p>No books found.</p>;
  }

  return (
    <div className="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Category</th>
            <th>Available</th>
            <th>Status</th>
            <th>Shelf</th>
            <th>Details</th>
            {showActions && <th>Admin Actions</th>}
          </tr>
        </thead>
        <tbody>
          {books.map((book) => (
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.isbn}</td>
              <td>{book.category}</td>
              <td>{book.availableCopies}/{book.totalCopies}</td>
              <td>
                <span className={`status status-${book.status.toLowerCase()}`}>{book.status}</span>
              </td>
              <td>{book.shelfNumber}</td>
              <td>
                <Link to={`/books/${book.id}`}>View</Link>
              </td>
              {showActions && (
                <td>
                  <button type="button" onClick={() => onEdit(book.id)}>Edit</button>
                  <button type="button" className="danger" onClick={() => onDelete(book.id)}>Delete</button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookTable;
