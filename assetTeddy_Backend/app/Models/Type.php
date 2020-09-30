<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Type extends Model
{
    //
    protected $table = 'asset_type_category';
    protected $fillable = [ 'name'];
}
