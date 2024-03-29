{
"dataList": [
<#list students as s>
    {
    "studentNo": "${s.studentNo!}",
    "name": "${s.name!}",
    "sex": "${s.sex!}",
    "subjects": [
    <#list s.subjects as subject>
        {
        "subjectName": "${subject.subjectName!}",
        "score": "${subject.score!}"
        }<#if subject_has_next>,</#if>
    </#list>
    ],
    "hobby": [<#list s.hobbys as hobby>"${hobby}"<#if hobby_has_next>,</#if></#list>]
    }
    <#if s_has_next>,</#if>
</#list>
]
}
