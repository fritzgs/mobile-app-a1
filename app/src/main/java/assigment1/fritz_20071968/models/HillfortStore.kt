package assigment1.fritz_20071968.models

interface HillfortStore
{
  fun findAll() : List<HillfortModel>
  fun create(hillfort : HillfortModel)
  fun update(hillfort : HillfortModel)
}