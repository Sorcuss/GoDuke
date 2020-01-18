name="bsm"
for i in {1..5}; do
    aws cognito-idp admin-create-user --user-pool-id us-east-1_JCWokP4yj --username ${name}0$i@${name}.pl --temporary-password Password0$i! --user-attributes Name="email",Value="${name}0$i@${name}.pl" Name="name",Value="${name}0$i@${name}.pl";
done