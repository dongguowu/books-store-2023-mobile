# Assignment #2: Multi-Screen CRUD Kotlin Multiplatform / Compose Multiplatform App

## [Dongguo Wu](https://github.com/dongguowu)

### Based on [AppKickstarter Template](https://github.com/JetBrains/compose-multiplatform-ios-android-template)

 <img src="readme_images/Screenshot.png" alt="Image Description" width="300" height="">

## Functionality

### Two functional screens

- Bookstore Screen, display a list of book cards where book can add to shopping cart, and update shopping cart info.
- Shopping Cart Screen, display all items related to books added to cart and manage them.

### Implement CRUD

#### 4 (CRUD) may work for a single item at a time

- Create, add a book to shopping cart
- Read, get an shopping cart item by book id
- Update, update the quantity of item
- Delete, delete the item from shopping cart

#### 1 (R) must display a list (i.e., getAll)

- Display all items on Shopping cart screen

## Layout

- use Scaffold as the whole layout.
- mainly use Column and Row in Scaffold body

message = "## Feature\n" + "\n" + "- Model-View-ViewModel \n" + "- AppKickstarter template\n" + "- Voyager Screen/ScreenModel\n" + "- Mongo Realm Sync database\n" + "- Stateless components\n" + "- Material design\n" + "- Layouts consistency"
