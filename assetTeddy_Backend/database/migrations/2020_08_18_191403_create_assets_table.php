<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAssetsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('assets', function (Blueprint $table) {
            $table->id();
            $table->Integer('serial_number');
            $table->bigInteger('barcode')->unique();
            $table->string('asset_type');
            $table->Text('description');
            $table->string('location');
            $table->string('department');
            $table->string('status');
            $table->string('created_by_user')->nullable();;
            $table->string('updated_by_user')->nullable();;
            $table->string('remark')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('assets');
    }
}
