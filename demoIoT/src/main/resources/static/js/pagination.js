function createPagination(totalPages, page, condition){
    let liTag = '';
    let active;
    let beforePage = totalPages > 2 ? page - 1 : 2;
    let afterPage = page + 1;
    if(condition === 'data')
        liTag += `<li class="first numb" onclick="searchData(1)"><span>1</span></li>`;
    else if(condition === 'action')
        liTag += `<li class="first numb" onclick="searchAction(1)"><span>1</span></li>`;

    if(page > 3)
    {
        liTag += `<li class="dots"><span>...</span></li>`;
    }

    if (page == totalPages && page > 2)
    {
        beforePage = beforePage - 2;
    }
    else if (page == totalPages - 1)
    {
        beforePage = beforePage - 1;
    }
    if (page == 1 && page + 2 < totalPages)
    {
        afterPage = afterPage + 2;
    }
    else if (page == 2 && page + 1 < totalPages)
    {
        afterPage  = afterPage + 1;
    }
    if(beforePage <= 2)
        beforePage = 2;
    for (var plength = beforePage; plength <= afterPage; plength++)
    {
        if (plength > totalPages)
        { //if plength is greater than totalPage length then continue
            continue;
        }
        if (plength == 0)
        { //if plength is 0 than add +1 in plength value
            plength = plength + 1;
        }
        if(page == plength)
        { //if page is equal to plength than assign active string in the active variable
            active = "active";
        }
        else
        { //else leave empty to the active variable
            active = "";
        }
        if(condition === 'data')
            liTag += `<li class="numb ${active}" onclick="searchData(${plength})"><span>${plength}</span></li>`;
        else if(condition === 'action')
            liTag += `<li class="numb ${active}" onclick="searchAction(${plength})"><span>${plength}</span></li>`;
    }
    if(page < totalPages - 1)
    { //if page value is less than totalPage value by -1 then show the last li or page
        if(page < totalPages - 2)
        { //if page value is less than totalPage value by -2 then add this (...) before the last li or page
            liTag += `<li class="dots"><span>...</span></li>`;
        }
        if(condition === 'data')
            liTag += `<li class="last numb" onclick="searchData(${totalPages})"><span>${totalPages}</span></li>`;
        else if(condition === 'action')
            liTag += `<li class="last numb" onclick="searchAction(${totalPages})"><span>${totalPages}</span></li>`;
    }
    if (page < totalPages) { //show the next button if the page value is less than totalPage(20)
        if (condition === 'data')
            liTag += `<li class="btn next" onclick="searchData(${page + 1})"><span>Next <i class="fas fa-angle-right"></i></span></li>`;
        else if (condition === 'action')
            liTag += `<li class="btn next" onclick="searchAction(${page + 1})"><span>Next <i class="fas fa-angle-right"></i></span></li>`;
    }
    element.innerHTML = liTag; //add li tag inside ul tag

    return liTag; //reurn the li tag
}