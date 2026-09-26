import { NavLink, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage";
import BookSearchPage from "./pages/BookSearchPage";
import BookDetailsPage from "./pages/BookDetailsPage";
import AvailabilityPage from "./pages/AvailabilityPage";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import AddEditBookPage from "./pages/AddEditBookPage";
import IssueReturnPage from "./pages/IssueReturnPage";

const App = () => {
  return (
    <div className="layout">
      <header>
        <h1>College Library Availability Check</h1>
        <nav>
          <NavLink to="/">Home</NavLink>
          <NavLink to="/search">Book Search</NavLink>
          <NavLink to="/availability">Availability</NavLink>
          <NavLink to="/admin">Admin</NavLink>
          <NavLink to="/admin/add">Add Book</NavLink>
          <NavLink to="/issue-return">Issue/Return</NavLink>
        </nav>
      </header>

      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/search" element={<BookSearchPage />} />
          <Route path="/books/:id" element={<BookDetailsPage />} />
          <Route path="/availability" element={<AvailabilityPage />} />
          <Route path="/admin" element={<AdminDashboardPage />} />
          <Route path="/admin/add" element={<AddEditBookPage />} />
          <Route path="/admin/edit/:id" element={<AddEditBookPage />} />
          <Route path="/issue-return" element={<IssueReturnPage />} />
        </Routes>
      </main>
    </div>
  );
};

export default App;
