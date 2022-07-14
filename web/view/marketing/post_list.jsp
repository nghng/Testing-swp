<%-- 
    Document   : post_list
    Created on : Jun 12, 2022, 1:18:35 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Post list</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
              integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/marketing/default_marketing.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/marketing/add_post.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/marketing/post_list.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/profile.css">
        <script src="https://kit.fontawesome.com/12bcef49b0.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>



    </head>

    <body>
        <!-- HEADER -->
        <jsp:include page="${pageContext.request.contextPath}../../view/header_for_staff.jsp"/>


        <section class="main">
            <!-- RIGHT CONTENT -->
            <a href="addblog" class="addlink_ver2">Add blog</a>

            <aside class="right">
                <!--Starting of the filter-->

                <c:choose>
                    <c:when test="${requestScope.posts.size() eq 0 or requestScope.posts == null}">
                        <div class="no__result">
                            There are no records matching the search query <br>
                            ${requestScope.errorMessage}
                        </div>

                    </c:when>
                    <c:otherwise>
                        <div class="table__data">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Post ID </th>
                                        <th>Title</th>
                                        <th>Category</th>
                                        <th>Feature</th>
                                        <th>Status</th>
                                        <th>Author</th>
                                        <th>Updated date</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.posts}" var="p">
                                        <tr>
                                            <td>${p.postID}</td>
                                            <td>${p.title}</td>
                                            <td>${p.subcategory.subcategoryName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.isFeatured}">
                                                        Supported
                                                    </c:when>
                                                    <c:otherwise>
                                                        No Supported
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.status}">
                                                        On
                                                    </c:when>
                                                    <c:otherwise>
                                                        Off
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${p.author.username}</td>
                                            <td>${p.updatedDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.status == true}">
                                                        <a href="changeblogstatus?postID=${p.postID}&status=false" class="action status__inactive">Hide</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="changeblogstatus?postID=${p.postID}&status=true" class="action status__active ">Show</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><a href="view?postID=${p.postID}" class="view view__alink">View</a></td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div id="pagination" class="pagination"></div>
                        </div>
                    </c:otherwise>
                </c:choose>



                <form action="bloglist" method="GET">

                    <section class="option__box" <c:if test="${requestScope.posts.size() eq 0 or requestScope.posts == null}">
                             style="width: 260.4px;"
                        </c:if>>
                        <div class="option__filter">
                            <div class="form-group">
                                <label  for="from">From</label>
                                <input inline type="date" class="form-control" name="from" id="from" value="${sessionScope.from}">
                            </div>
                            <div class="form-group">
                                <label for="to">To</label>
                                <input type="date" name="to" class="form-control" id="to" value="${sessionScope.to}">
                            </div>


                            <div class="option__searchbar">
                                <div class="form-group">
                                    <label for="">Author</label>
                                    <input type="text" name="author" class="form-control"  value="${sessionScope.author}">
                                </div>
                                <div class="form-group">
                                    <label for="">Title</label>
                                    <input type="text" name="search" class="form-control"  value="${sessionScope.title}">
                                </div>
                                <div class="form-group">
                                    <label for="status">Status</label>
                                    <select name="status" class="form-control" id="status">
                                        <option value="true" <c:if test="${sessionScope.status == 'true'}">selected</c:if>>Shown</option>
                                        <option value="false" <c:if test="${sessionScope.status == 'false'}">selected</c:if>>Hide</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="status">Feature</label>
                                        <select name="isFeatured" class="form-control">
                                            <option value="true" <c:if test="${sessionScope.isFeatured == 'true'}">selected</c:if>>Supported</option>
                                        <option value="false" <c:if test="${sessionScope.isFeatured == 'false'}">selected</c:if>>No supported</option>
                                        </select>
                                    </div>











                                    <div class="option__checkbox">
                                        <h3>Category</h3>
                                        <div class="option__options-value">
                                            <div class="accordion accordion-flush" id="accordionFlushExample">
                                            <c:forEach items="${requestScope.categories}" var="cate">
                                                <div class="accordion-item">
                                                    <h2 class="accordion-header" id="flush-headingOne">
                                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse${cate.categoryID}" aria-expanded="false" aria-controls="flush-collapseOne">
                                                            ${cate.categoryName}
                                                        </button>
                                                    </h2>
                                                    <div id="flush-collapse${cate.categoryID}" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                                                        <div class="accordion-body">
                                                            <c:forEach items="${cate.subcategories}" var="sc">
                                                                <div class="subcategory">
                                                                    <c:set var="contains" value="false"/>
                                                                    <c:forEach items="${sessionScope.subcategory}" var="s">
                                                                        <c:if test="${s eq sc.subcategoryID}">
                                                                            <c:set var="contains" value="true"/>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <input type="checkbox" name="subcategory" value="${sc.subcategoryID}"
                                                                           <c:if test="${contains}">
                                                                               checked
                                                                           </c:if>
                                                                           > <span>${sc.subcategoryName}</span>
                                                                </div>
                                                            </c:forEach></div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>

                                <div class="option__sort">
                                    <select name="sort">
                                        <option selected disabled>Sort by:</option>
                                        <option value="DESC">Date added(newest)</option>
                                        <option value="ASC">Date added(oldest)</option>
                                    </select>
                                </div>
                                <div class="search__button" id="searchButton">
                                    <button type="submit">Search</button>
                                </div>
                                <div class="contact__link">
                                    <a href="#">Contact Information</a>
                                </div>
                                </form>
                            </div>
                    </section>
            </aside>
            <jsp:include page="${pageContext.request.contextPath}../../view/user_popup.jsp"/>



        </section>
        <jsp:include page="${pageContext.request.contextPath}../../view/footer.jsp"/>


        <script src="${pageContext.request.contextPath}/js/customer/customerRegistration.js"></script>
        <script src="${pageContext.request.contextPath}/js/userPopup.js"></script>
        <script src="${pageContext.request.contextPath}/js/script.js"></script>
        <script src="${pageContext.request.contextPath}/js/userPopup.js"></script>
        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <script src="${pageContext.request.contextPath}/js/changepass.js"></script>
        <script src="${pageContext.request.contextPath}/js/common/home.js"></script>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script>pagger("pagination", ${requestScope.pageindex}, ${requestScope.totalpage}, 3, "${requestScope.url}", "${requestScope.querystring}");</script>


    </body>
</html>
