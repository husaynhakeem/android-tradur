package io.husaynhakeem.tradur.feature.posts

import io.husaynhakeem.tradur.core.model.Post


object PostsRepository {

    fun get(): Array<Post> = arrayOf(
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/11/12/13/37/forest-2942477_1280.jpg",
                    username = "frenchPerson",
                    description = "Mes choses préférées dans la vie ne coûtent pas d'argent. Il est vraiment clair que la ressource la plus précieuse que nous avons tous est le temps.",
                    publicationDate = "1 hour ago"),
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/02/15/10/39/food-2068217_1280.jpg",
                    username = "chinesePerson",
                    description = "我生活中最喜欢的东西不花钱。很明显，我们拥有的最宝贵的资源是时间。",
                    publicationDate = "21 hours ago"),
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/10/07/15/27/wolf-2826741_1280.jpg",
                    username = "arabPerson",
                    description = "الأشياء المفضلة لدي في الحياة لا تكلفك أي أموال. من الواضح حقًا أن أغلى مورد لدينا جميعًا هو الوقت.",
                    publicationDate = "2 days ago"),
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/11/06/13/45/cap-2923682_1280.jpg",
                    username = "germanPerson",
                    description = "Meine Lieblingsdinge im Leben kosten kein Geld. Es ist wirklich klar, dass die wertvollste Ressource, die wir alle haben, die Zeit ist.",
                    publicationDate = "1 week ago"),
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/05/30/12/21/green-tea-2356770_1280.jpg",
                    username = "koreanPerson",
                    description = "인생에서 가장 좋아하는 것은 돈을 들이지 않습니다. 우리 모두가 가지고있는 가장 귀중한 자원은 시간이라는 것이 분명합니다.",
                    publicationDate = "3 weeks ago"),
            Post(
                    imageUrl = "https://cdn.pixabay.com/photo/2017/11/03/16/41/pair-2914879_1280.jpg",
                    username = "spanishPerson",
                    description = "Mis cosas favoritas en la vida no cuestan dinero. Está muy claro que el recurso más valioso que todos tenemos es el tiempo.",
                    publicationDate = "23 weeks ago"))
}