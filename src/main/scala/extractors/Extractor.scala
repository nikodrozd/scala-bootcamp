package extractors

object Extractor {

  val inUaStr: String = "in.ua"
  val gmailComStr: String = "gmail.com"

  def filterNotInUa(domains: List[String]): List[String] = {
    domains.filterNot {
      case Domain(f, s, _*) if s"$s.$f" == inUaStr => true
      case _ => false
    }
  }

  def filterGmail(emails: Array[String]): Array[Unit] = {
    for (email <- emails) yield {
      email match {
        case Email(_, domain) if domain == gmailComStr => println(email)
        case _ =>
      }
    }
  }

//  def filterGmail(emails: Array[String]) = {
//    for {
//      emailStr <- emails
//      email <- Email.unapply(emailStr) if email._2 == gmailComStr
//    } yield (println(emailStr))
//  }
}

object Email {

  def apply(localPart: String, domain: String) = s"$localPart@$domain"

  def unapply(email: String): Option[(String, String)] = {
    email.split("@") match {
      case Array(localPart, domain) => Some((localPart, domain))
      case _ => None
    }
  }

}

object Domain {

  def unapplySeq(domain: String): Option[Array[String]] = {
    Some(domain.split("\\.").reverse)
  }
}
