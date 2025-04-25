# spring-boot-intro-project
 This project includes [list of features or technologies used, e.g. Spring Data JPA, Thymeleaf etc.].

## How new product saving happen?
## ✅ Summary Diagram (With Code Flow)
Check the [Product Flow](./docs/new-product-save.md) for how adding products works.


```plaintext
[index.html] -> Click "Add New Product"
     ↓
GET /new  → Controller returns new_product.html with empty Product
     ↓
[new_product.html] → User fills form and submits
     ↓
POST /save → Controller saves Product → redirect to "/"
     ↓
GET / → Controller fetches list → index.html renders updated products
```


---

## 🟢 1. User clicks "Add New Product"

In your `index.html`, you’ll have a button or link like this:

```html
<!-- include: index.html -->
<a href="/new">Add New Product</a>
```

When the user clicks it, it triggers a **GET request** to `/new`.

---

## 🟡 2. GET /new → Load the form

### 🔧 Java Controller:

```java
// include: ProductController.java
@RequestMapping("/new")
public String showProductById(Model model){
    Product product = new Product();                // Create empty Product
    model.addAttribute("product", product);         // Add to model
    return "new_product";                           // Load form view
}
```

---

## 🟠 3. Display the form: `new_product.html`

### 🌐 HTML/Thymeleaf Form:

```html
<!-- include: new_product.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Add New Product</title>
</head>
<body>
<div align="center">
    <h2>Add New Product</h2>
    <form th:action="@{/save}" th:object="${product}" method="post">
        <table>
            <tr><td>Name:</td><td><input type="text" th:field="*{name}" /></td></tr>
            <tr><td>Brand:</td><td><input type="text" th:field="*{brand}" /></td></tr>
            <tr><td>Modern:</td><td><input type="text" th:field="*{modern}" /></td></tr>
            <tr><td>Price:</td><td><input type="text" th:field="*{price}" /></td></tr>
            <tr><td colspan="2"><input type="submit" value="Save" /></td></tr>
        </table>
    </form>
</div>
</body>
</html>
```

**What happens:**
- Spring sends an empty `Product` to the form.
- Thymeleaf binds the form fields to the `Product` object.
- When the user submits, it sends a POST request to `/save`.

---

## 🔴 4. POST /save → Save product and redirect

### 🔧 Java Controller:

```java
// include: ProductController.java
@RequestMapping(value = "/save", method = RequestMethod.POST)
public String saveProduct(@ModelAttribute("product") Product product){
    productService.save(product);  // Save to DB
    return "redirect:/";           // Redirect to homepage
}
```

**What happens:**
- Spring binds form data → `Product` object.
- You call `productService.save(product)` (likely using a `JpaRepository`).
- Redirects to `/`, which is your homepage.

---

## 🟣 5. Redirect to `/` and show the updated list

### 🔧 Controller for homepage:

```java
// include: ProductController.java
@RequestMapping("/")
public String viewHomePage(Model model) {
    List<Product> listProducts = productService.listAll();     // Get all products
    model.addAttribute("listProducts", listProducts);          // Send to view
    return "index";                                            // Show index.html
}
```

---

## 🔵 6. Show updated list in `index.html`

```html
<!-- include: index.html -->
<table border="1">
    <thead>
    <tr>
        <th>ID</th><th>Name</th><th>Brand</th><th>Modern</th><th>Price</th><th>Action</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="product : ${listProducts}">
        <td th:text="${product.id}">1</td>
        <td th:text="${product.name}">Name</td>
        <td th:text="${product.brand}">Brand</td>
        <td th:text="${product.modern}">Modern</td>
        <td th:text="${product.price}">0.00</td>
        <td>
            <a th:href="@{'/edit' + ${product.id}}">Edit</a> |
            <a th:href="@{'/delete/' + ${product.id}}"
               onclick="return confirm('Are you sure?')">Delete</a>
        </td>
    </tr>
    </tbody>
</table>
```

---

## ✅ Summary Diagram (With Code Flow)

```plaintext
[index.html] -> Click "Add New Product"
     ↓
GET /new  → Controller returns new_product.html with empty Product
     ↓
[new_product.html] → User fills form and submits
     ↓
POST /save → Controller saves Product → redirect to "/"
     ↓
GET / → Controller fetches list → index.html renders updated products
```
