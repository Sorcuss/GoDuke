# GoDuke

Contract:

recruiter:
{
    id: integer
    username: string
    firstname: string
    lastname:string
    password: string
    email: string
}
candidate:
{
    id: integer
    firstname: string
    lastname:string
    email: string
    password: string
}
test:
{
    id: integer
    languages: list of strings
    name: string
    questions: list of questions
    creator: recruiter id
    candidates: candidates ids
}
question:
{
    number: integer
    type: string
    language: string 
    question: string
    options: list of strings
    
}


answers:
{
    id: int
    testId: int
    candidateId: int
    Map<questionId<--String, String>: answers
}
