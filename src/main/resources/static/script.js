// Modal open/close
(function(){
  const loginModal = document.getElementById('loginModal');
  const registerModal = document.getElementById('registerModal');
  const addBookModal = document.getElementById('addBookModal');
  const loginBtn = document.getElementById('loginBtn');
  const registerBtn = document.getElementById('registerBtn');
  const addBookBtn = document.getElementById('addBookBtn');
  
  function open(modal){ if(modal) modal.classList.add('open'); }
  function close(modal){ if(modal) modal.classList.remove('open'); }
  
  if(loginBtn) loginBtn.addEventListener('click', ()=> open(loginModal));
  if(registerBtn) registerBtn.addEventListener('click', ()=> open(registerModal));
  if(addBookBtn) addBookBtn.addEventListener('click', ()=> open(addBookModal));
  
  document.querySelectorAll('.modal .close').forEach(x => x.addEventListener('click', (e)=>{
    const modal = e.target.closest('.modal');
    close(modal);
  }));
  
  window.addEventListener('keydown', (e)=>{
    if(e.key === 'Escape'){ 
      close(loginModal); 
      close(registerModal); 
      close(addBookModal);
    }
  });
})();

// Book management functionality
(function(){
  const booksGrid = document.getElementById('booksGrid');
  const categoryTabs = document.querySelectorAll('.tab-btn');
  const addBookForm = document.getElementById('addBookForm');
  
  // Load books when page loads
  document.addEventListener('DOMContentLoaded', function() {
    loadBooks();
  });
  
  // Handle category tab clicks
  categoryTabs.forEach(tab => {
    tab.addEventListener('click', function() {
      // Remove active class from all tabs
      categoryTabs.forEach(t => t.classList.remove('active'));
      // Add active class to clicked tab
      this.classList.add('active');
      
      const category = this.getAttribute('data-category');
      loadBooks(category);
    });
  });
  
  // Load books function
  function loadBooks(category = 'all') {
    let url = '/books';
    if (category !== 'all') {
      url = `/books/category/${category}`;
    }
    
    fetch(url)
      .then(response => response.json())
      .then(books => {
        displayBooks(books);
      })
      .catch(error => {
        console.error('Error loading books:', error);
        booksGrid.innerHTML = '<p>Error loading books. Please try again.</p>';
      });
  }
  
  // Display books in grid
  function displayBooks(books) {
    if (books.length === 0) {
      booksGrid.innerHTML = '<p>No books found in this category.</p>';
      return;
    }
    
    booksGrid.innerHTML = books.map(book => `
      <div class="book-card">
        <div class="book-title">${book.title}</div>
        <div class="book-author">by ${book.author}</div>
        <div class="book-meta">
          <span class="book-year">${book.publishedYear || 'N/A'}</span>
          <span class="book-copies">${book.copiesAvailable}/${book.copiesTotal} available</span>
        </div>
        <div class="book-actions">
          <button class="btn btn-secondary btn-sm" onclick="viewBook(${book.id})">View</button>
          ${isLibrarian ? `
            <button class="btn btn-outline btn-sm" onclick="editBook(${book.id})">Edit</button>
            <button class="btn btn-outline btn-sm" onclick="deleteBook(${book.id})" style="color: #ef4444;">Delete</button>
          ` : ''}
        </div>
      </div>
    `).join('');
  }
  
  // Add book form submission
  if (addBookForm) {
    addBookForm.addEventListener('submit', function(e) {
      e.preventDefault();
      
      const formData = new FormData(this);
      const book = {
        isbn: formData.get('isbn'),
        title: formData.get('title'),
        author: formData.get('author'),
        publishedYear: parseInt(formData.get('publishedYear')),
        category: formData.get('category'),
        academicYear: formData.get('academicYear'),
        subject: formData.get('subject'),
        copiesTotal: parseInt(formData.get('copiesTotal'))
      };
      
      fetch('/books', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(book)
      })
      .then(response => {
        if (response.ok) {
          alert('Book added successfully!');
          this.reset();
          document.getElementById('addBookModal').classList.remove('open');
          loadBooks(); // Reload books
        } else {
          alert('Error adding book. Please try again.');
        }
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Error adding book. Please try again.');
      });
    });
  }
  
  // Global functions for book actions
  window.viewBook = function(id) {
    alert('View book functionality - Book ID: ' + id);
  };

  window.editBook = function(id) {
    alert('Edit book functionality - Book ID: ' + id);
  };

  window.deleteBook = function(id) {
    if (confirm('Are you sure you want to delete this book?')) {
      fetch(`/books/${id}`, {
        method: 'DELETE'
      })
      .then(response => {
        if (response.ok) {
          alert('Book deleted successfully!');
          loadBooks(); // Reload books
        } else {
          alert('Error deleting book. Please try again.');
        }
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Error deleting book. Please try again.');
      });
    }
  };

  // Global logout function
  // window.logout = function() {
  //   fetch('http://localhost:8080/logout', {
  //       method: 'POST',
  //       headers: {
  //           'Content-Type': 'application/json'
  //       }
  //   })
  //   .then(response => {
  //       if (response.ok) {
  //           console.log('Logged out successfully');
  //           window.location.href = '/login'; // Redirect to login page
  //       } else {
  //           console.log('Error logging out');
  //       }
  //   })
  //   .catch(error => {
  //       console.error('Logout request failed:', error);
  //   });
  // };


function deleteBook(bookId) {
    fetch(`http://localhost:8080/books/${bookId}`, {
        method: 'DELETE',  // HTTP DELETE method
        headers: {
            'Content-Type': 'application/json',
            // Add authorization or any headers needed
        }
    })
    .then(response => {
        if (response.ok) {
            console.log('Book deleted successfully');
            // Optionally, refresh the list of books or redirect
        } else {
            console.log('Error deleting the book');
        }
    })
    .catch(error => {
        console.error('Delete request failed:', error);
    });
}


  
  // Check if user is librarian (this would be set by the server)
  const isLibrarian = document.body.getAttribute('data-is-librarian') === 'true';
  
})();




