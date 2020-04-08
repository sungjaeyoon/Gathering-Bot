<template>
  <div class="container">
    <div class="row">
      <div class="col m-5 p-5">
        <div id="main-text">
          Gathering-Bot
        </div>
        <div class="mt-5 pt-3 sub-text">
          Automate Your team answer.
          Gathering-Bot will save your time.
          Add answer & show result!
        </div>
        <div class="mt-5 pt-3">
          <!-- Button trigger modal -->
          <button type="button" class="btn btn-outline-primary btn-lg m-2" data-toggle="modal"
                  data-target="#exampleModal">Create New Form
          </button>
          <!-- Modal -->
          <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
               aria-labelledby="exampleModalLabel"
               aria-hidden="true">
            <AddModal/>
          </div>
          <!--Result-->
          <button type="button" class="btn btn btn-outline-success btn-lg m-2" onclick="location.href='result'">Show Result</button>
        </div>
      </div>
      <div class="col m-5 p-5">
        <img src="../assets/robot.png" class="img-fluid" alt="Responsive image">
      </div>
    </div>
  </div>
</template>

<script>
import AddModal from './AddModal'
export default {
  name: 'MainPage',
  components: {
    'AddModal': AddModal
  }
}
</script>

<style scoped>
  body{
    background-color: whitesmoke;
  }

  #main-text{
    color: #343a40!important;
    font-size: 3.5em;
    font-weight: 600;
  }

  .sub-text{
    color: #343a40!important;
    font-size: 1.5em;
  }

  .box{
    background-color: white;
  }
</style>
