# GoDuke

Contract:

recruiter: <br>
{<br>
    id: integer<br>
    username: string<br>
    firstname: string<br>
    lastname:string<br>
    password: string<br>
    email: string<br>
}
candidate:<br>
{<br>
    id: integer<br>
    firstname: string<br>
    lastname:string<br>
    email: string<br>
    password: string<br>
}<br>
test:<br>
{<br>
    id: integer<br>
    languages: list of strings<br>
    name: string<br>
    questions: list of questions<br>
    creator: recruiter id<br>
    candidates: candidates ids<br>
}
question:<br>
{<br>
    number: integer<br>
    type: string<br>
    language: string <br>
    question: string<br>
    options: list of strings<br>
}<br>


answers:<br>
{<br>
    id: int<br>
    testId: int<br>
    candidateId: int<br>
    Map<questionId<--String, String>: answers<br>
}<br>
