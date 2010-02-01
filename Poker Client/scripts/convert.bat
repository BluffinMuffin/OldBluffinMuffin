@echo off
echo svm_type c_svc> tmp
echo kernel_type linear>> tmp
echo nr_class 2| sed -e "s/#//" >> tmp
grep "^[0-9.-]\+ [0-9]:" %1 | wc -l | sed -e "s/\(.*\)/total_sv \1/" >> tmp
head -n 11 %1 | tail -n 1 | sed -e "s/^\([0-9\.-]\+\).*/rho \1/" >> tmp
echo label 1 -1>> tmp
echo nr_sv > tmp0
grep "^[0-9.]\+ [0-9]:" %1 | wc -l | sed -e "s/\(.*\)/\1 /" > tmp1
grep "^\-[0-9.]\+ [0-9]:" %1 | wc -l > tmp2
cat tmp0 tmp1 tmp2 | tr -d "\r\n" | sed -e "s/\(.*\)/\1\n/" >> tmp

del tmp0
del tmp1
del tmp2

echo SV>> tmp
grep "^[0-9.-]\+ [0-9]:" %1 >> tmp

cat tmp | tr -d "\r" > %2

del tmp