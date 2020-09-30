<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Asset extends Model
{
    //
    protected $table = 'assets';
    protected $fillable = [ 'serial_number', 'barcode', 'asset_type', 'description', 'location', 'department', 'status','remark', 'created_by_user', 'updated_by_user'];
}
