const API_URL = "http://localhost:8080/products";

// Fetch and display products
async function fetchProducts() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Failed to fetch products");
        const products = await response.json();

        const tableBody = document.getElementById("productTableBody");
        tableBody.innerHTML = ""; // Clear table

        products.forEach(product => {
            const row = `<tr>
            <td>${product.prodId}</td>   
            <td>${product.prodName}</td> 
            <td>${product.price}</td>
            <td>
                <button onclick="deleteProduct(${product.prodId})">Delete</button>
                <button onclick="updateProduct(${product.prodId})">Update</button>
            </td>
        </tr>`;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        alert(error.message);
    }
}

// Add product
document.getElementById("productForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("productName").value;
    const price = document.getElementById("productPrice").value;

    if (name.length > 50) {
        alert("Product name is too long (max 50 characters)");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ prodName: name, price }) 
        });

        if (!response.ok) throw new Error(await response.text());

        alert("Product added successfully!");
        fetchProducts();
    } catch (error) {
        alert(error.message);
    }
});

// Delete product
async function deleteProduct(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });

        if (!response.ok) throw new Error(await response.text());

        alert("Product deleted successfully!");
        fetchProducts();
    } catch (error) {
        alert(error.message);
    }
}

// Update product
async function updateProduct(id) {
    const newName = prompt("Enter new name:");
    const newPrice = prompt("Enter new price:");

    if (newName && newPrice) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ prodId: id, prodName: newName, price: parseFloat(newPrice) })             });

            if (!response.ok) throw new Error(await response.text());

            alert("Product updated successfully!");
            fetchProducts();
        } catch (error) {
            alert(error.message);
        }
    }
}

// Load products on page load
fetchProducts();