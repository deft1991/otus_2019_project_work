Проектная работа Otus
<br>
Тема: Платформа для написания новостей для Telegram каналов
<br>
Для запуска --> 
<br>
выкачиваем код из реквеста. (проверил на 3х ноутах) --> 
<br>
запускаем 3 сервиса как спринг бутовые приложения 
<br>
(при запуске телеграм бота нужно в логах увидеть что все нормально зарегалось в телеграм апи: 
<br>
порой косячит коннект в Питере) --> 
<br>
есть 2 точки входа: фронт http://localhost:8082/ui/ 
<br>
и telegram bot OtusChatWithTeacherBot (находится поиском по телеграму) --> 
<br>
на фронте можно залогиниться с логином lala и паролем password --> 
<br>
на вкладке http://localhost:8082/ui/news видны все новости 
<br>
(позже постараюсь добавить апрув на новости что бы постилось не все, если текущий код устроит) --> 
<br>
с фронта можно добавить новости которые будут поститься в те чаты которые есть у бота и 
<br>
отправленным новостям проставляется флаг IS_PUBLISHED.
В телеграмм боте необходимо ввести /start что бы начать работу с ботом 
<br>
(это стандартная команда telegram) --> 
<br>
далее необходимо задать имя пользователя от которого будет поститься новость (пока имя пользователя не меняю) --> 
<br>
далее нужно использовать команду /recommend и написать новость, создание новости можно увидеть на фронте --> 
<br>
через 15 секунд (такой интервал пока установлен для тестирования) бот отправит новость во все доступные чаты