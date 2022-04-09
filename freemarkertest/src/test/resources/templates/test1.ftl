<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
    Hello ${name}!
    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>金额</td>
            <td>出生日期</td>
        </tr>

        <#list stus as stu>
            <tr>
                <td>${stu_index + 1}</td>
                <td>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.money}</td>
                <!-- <td>stu.birthday</td> -->
            </tr>
        </#list>

        姓名: ${stuMap['stu1'].name}
        姓名: ${stuMap.stu1.name}

        <#list stuMap?keys as key>
            <tr>
                <td>${key}</td>
                <td>${stuMap[key].name}</td>
                <td>${stuMap[key].age}</td>
                <td>${stuMap[key].money}</td>
                <!-- <td>stu.birthday</td> -->
            </tr>
        </#list>
    </table>
</body>
</html>
