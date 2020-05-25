<template>
  <div>
    <!-- Modal -->
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">폼 형식을 선택하세요!</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <!--modal body-->
        <div class="modal-body">
          <div class="container">
            <div class="card ml-2" style="width: 18rem;">
              <div class="m-2">
                <img src="../assets/ok.png" class="card-img-top" alt="...">
              </div>
              <div class="card-body m-2">
                <p class="card-text">단순하게 O / X 로 응답할 수 있는 형식입니다.</p>
                <button type="button" class="btn btn-primary btn-lg m-2"
                        onclick="location.href='create/ox'">O/X Form
                </button>
              </div>
            </div>
            <div class="card mr-2" style="width: 18rem;">
              <div class="m-2">
                <img src="../assets/spreadsheet.png" class="card-img-top" alt="...">
              </div>
              <div class="card-body">
                <p class="card-text">직접 엑셀 테이블을 만들고 많은 응답을 처리 할 수 있는 형식입니다.</p>
                <button type="button" class="btn btn-primary btn-lg m-2"
                        onclick="location.href='create/table'">Table Form
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddModal"
}
</script>

<style scoped>

</style>
