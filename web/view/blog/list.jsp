<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blogs</title>
        <!-- FontAwesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
              integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Bootstrap's CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/global.css">
        <link rel="stylesheet" href="css/header.css">
        <link rel="stylesheet" href="css/popup.css">
        <link rel="stylesheet" href="css/footer.css">
        <link rel="stylesheet" href="css/blog/list.css">
    </head>

    <body>
        <header>
            <div class="heading_logo">
                <p>LOGO</p>
            </div>
            <nav>
                <ul class="nav_links">
                    <li><a href="../../../index.html">Home</a></li>
                    <li><a href="../subject/subjectlist.html">Subject</a></li>
                    <li><a href="list.html">Blog</a></li>
                    <li><a href="#" class="login" id="loginButton">Log in</a></li>
                </ul>
            </nav>
        </header>
        <div class="heading">
            <h1>LASTEST BLOG</h1>
        </div>

        <div class="main">
            <section class="post__list">
                <c:forEach items="${requestScope.posts}" var="p">


                    <div class="post__item">
                        <div class="post__thumbnail"><img src="images/blog/${p.thumbnailUrl}" alt="alt"/></div>
                        <div class="post__content">
                            <h3>${p.title}</h3>
                            <div class="post__info">
                                <p><i class="fa fa-align-justify"></i> Category: ${p.category.categoryName}</p>
                                <p><i class="fa fa-calendar-alt"></i> Post on: ${p.updatedDate}</p>
                            </div>
                            <p class="post__review">${p.briefInfo}</p>
                            <div class="post__detail">
                                <a href="detail.html">More Details</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <div class="pagination">
                    <ul>
                        <li><a href="bloglist?page=1"><<</a></li>
                        <li><a href="bloglist?page=${requestScope.pageindex - 1}"><</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">${requestScope.pageindex}</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="bloglist?page=${requestScope.pageindex + 1}">></a></li>
                        <li><a href="bloglist?page=${requestScope.totalpage}">>></a></li>
                    </ul>
                </div>
            </section>


            <!-- RIGHT -->
            <section class="option__box">
                <div class="option__filter">
                    <div class="option__searchbar">
                        <form action="#">
                            <input type="text" placeholder="Type something to search...">
                            <!-- <button type="submit">Search</button> -->
                        </form>
                    </div>
                    <div class="option__checkbox">
                        <h5>Category</h5>
                        <div class="option__options-value">
                            <c:forEach items="${requestScope.categories}" var="cate">
                                <div class="option__options-value_item">
                                    <input type="checkbox" name="${cate.categoryID}" id=""> <span>${cate.categoryName}</span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="option__sort">
                        <select name="" id="">
                            <option value="All">All</option>
                            <option value="">SortItem1</option>
                            <option value="">SortItem2</option>
                        </select>
                    </div>
                    <div class="search__button">
                        <button type="submit">Search</button>
                    </div>
                    <div class="contact__link">
                        <a href="#">Contact Information</a>
                    </div>
                </div>
            </section>
        </div>

        <!-- POPUP -->
        <section class="popup">
            <div class="popup__content">
                <img src="images/close.png" alt="" class="close">

                <div class="popup__login-form">
                    <h2>Welcome to Quiz Practice</h2>
                    <div class="form__login">
                        <form action="#">
                            <input type="text" name="email" id="emailLogin" placeholder="Enter your email">
                            <input type="text" name="password" id="password" placeholder="Enter your password">
                            <div class="popup__reset">
                                <a href="#">Forgot password?</a>
                            </div>
                            <div class="form__button">
                                <button type="submit">Login</button>
                            </div>
                        </form>
                    </div>
                    <div class="popup__signup">
                        <a href="#">Don't have any account? Sign up here</a>
                    </div>
                </div>

                <div class="popup__signup-form" style="display: none;">
                    <i class="fa fa-arrow-left"></i>
                    <h2>Register for Quiz Practice</h2>
                    <div class="form_signup">
                        <form action="#">
                            <input type="text" name="firstName" id="firstName" placeholder="First Name">
                            <input type="text" name="lastName" id="lastName" placeholder="Last Name">
                            <input type="text" name="email" id="emailSignup" placeholder="Email">
                            <input type="text" name="phone" id="phone" placeholder="Phone Number">
                            <input type="password" name="password" id="password" placeholder="Password">
                            <input type="password" name="confirmPassword" id="confirmPassword"
                                   placeholder="Confirm password">
                            <div class="form__button">
                                <button type="submit">Register</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="popup__reset-form" style="display: none;">
                    <i class="fa fa-arrow-left"></i>
                    <h2>Reset Password</h2>
                    <div class="form__reset">
                        <form action="#">
                            <input type="text" name="email" id="emailReset"
                                   placeholder="Enter your email to reset your password">
                            <div class="form__button">
                                <button type="submit">Verify your email</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <footer>
            <p>COPYRIGHT</p>
        </footer>

        <script src="js/script.js"></script>
    </body>

</html>