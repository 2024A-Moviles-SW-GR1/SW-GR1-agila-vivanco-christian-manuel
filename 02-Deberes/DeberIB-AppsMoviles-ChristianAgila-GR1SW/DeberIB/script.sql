SELECT
    a.au_id, a.au_lname, a.au_fname,
    t.title_id, t.title,
    p.pub_id, p.pub_name,
    s.stor_id, s.stor_name,
    sl.ord_num, sl.qty, sl.payterms,
    e.emp_id, e.fname, e.lname,
    j.job_desc,
    d.discounttype,
    r.royalty,
    pi.pr_info
FROM
    authors a
        LEFT JOIN titleauthor ta ON a.au_id = ta.au_id
        LEFT JOIN titles t ON ta.title_id = t.title_id
        LEFT JOIN publishers p ON t.pub_id = p.pub_id
        LEFT JOIN pub_info pi ON p.pub_id = pi.pub_id
        LEFT JOIN employee e ON p.pub_id = e.pub_id
        LEFT JOIN jobs j ON e.job_id = j.job_id
        LEFT JOIN sales sl ON t.title_id = sl.title_id
        LEFT JOIN stores s ON sl.stor_id = s.stor_id
        LEFT JOIN discounts d ON s.stor_id = d.stor_id
        LEFT JOIN roysched r ON t.title_id = r.title_id
    GO